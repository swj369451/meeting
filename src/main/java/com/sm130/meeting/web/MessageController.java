package com.sm130.meeting.web;

import com.sm130.meeting.po.Message;
import com.sm130.meeting.po.Room;
import com.sm130.meeting.po.RoomApply;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.MessageService;
import com.sm130.meeting.service.RoomApplyService;
import com.sm130.meeting.service.RoomService;
import com.sm130.meeting.service.UserService;
import com.sm130.meeting.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private RoomApplyService roomApplyService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public String listMessage(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        Page<Message> messages = messageService.listMeeting(pageable, user.getId());
        model.addAttribute("page",messages);
        return "admin/message";
    }

    @GetMapping("/permit/{applyResult}/{messageId}/{applicantId}/{relate}")
    public String MeetingApplyResult(@PathVariable Long messageId, @PathVariable Long applyResult,
                                     HttpSession session, @PathVariable Long applicantId, @PathVariable Long relate){
//        修改消息状态
        messageService.changeById(messageId,applyResult);

        User user = (User) session.getAttribute("user");
        User applicant = userService.findUser(applicantId);
        RoomApply roomApply = roomApplyService.findById(relate);
        Long roomId = roomApply.getRoomId();
        Room room = roomService.getById(roomId);
        Date time = roomApply.getTime();

//        更新会议申请
        roomApply.setPermission(applyResult);
        roomApplyService.save(roomApply);
//        会议申请消息反馈消息反馈
        String content;
        if(applyResult==1){
             content = user.getNickname()+ ":同意了<"+ room.getName()+">"+ DateUtils.date2String(time) +"的申请";
            messageService.save(new Message(null,user.getId(),applicantId,content,1L,0L,new Date(),relate));
        }else{
             content = user.getNickname()+ ":拒绝了了<"+ room.getName()+">"+ DateUtils.date2String(time) +"的申请";
            messageService.save(new Message(null,user.getId(),applicantId,content,2L,0L,new Date(),relate));
        }
        return "redirect:/message/list";
    }

}
