package com.sm130.meeting.web;

import com.sm130.meeting.po.Message;
import com.sm130.meeting.po.Room;
import com.sm130.meeting.po.RoomApply;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.MessageService;
import com.sm130.meeting.service.RoomApplyService;
import com.sm130.meeting.service.RoomService;
import com.sm130.meeting.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomApplyService roomApplyService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    public String about(Model model) {
        model.addAttribute("rooms",roomService.getAll());
        return "room";
    }

    @PostMapping("/search")
    public String search(String typeId, String title, Model model) {
        List<Room> allBySearch = roomService.getAllBySearch(title, typeId);
        model.addAttribute("rooms",allBySearch);
//        这里返回的是html的名称room.html
        return "room :: rootList";
    }

    @PostMapping("/apply")
    public String apply(String roomid, Model model, HttpSession session, RedirectAttributes attributes){
//        if(session.getAttribute("user")==null){
//            attributes.addFlashAttribute("message", "请登录后申请");
//            return "redirect:/room/list";
//        }
        Room room = roomService.getById(Long.parseLong(roomid));
        model.addAttribute("room",room);
        return "room-input";
    }

    @PostMapping("/applyadd")
    public String applyAdd(String content, String time, Long roomid,Long managerid,String roomname,
                           RedirectAttributes attributes,HttpSession session){
//        Date date = new Date(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(5, 7)), Integer.parseInt(time.substring(8, 10)));
        RoomApply roomApply = new RoomApply();
        roomApply.setContent(content);
        roomApply.setTime(DateUtils.String2Date(time));
        User user = (User) session.getAttribute("user");
        roomApply.setApplyUserId(user.getId());
        roomApply.setPermission(0L);
        roomApply.setRoomId(roomid);
        roomApply.setManagerId(managerid);
        roomApplyService.save(roomApply);

//        储存消息
        String msg = user.getNickname()+":申请<"+roomname+">到"+time+",申请原因："+content;
        Message message = new Message(null,user.getId(),managerid,msg,0L,0L,new Date(),roomApply.getId());
        messageService.save(message);

        attributes.addFlashAttribute("message", "申请成功");
        return "redirect:/room/list";
    }
}
