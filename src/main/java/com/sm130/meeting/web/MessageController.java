package com.sm130.meeting.web;

import com.sm130.meeting.po.Message;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.MessageService;
import com.sm130.meeting.service.RoomApplyService;
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

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private RoomApplyService roomApplyService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    public String listMessage(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        Page<Message> messages = messageService.listMeeting(pageable, user.getId());
        model.addAttribute("page",messages);
        return "admin/message";
    }

    @GetMapping("/permit/{permit}/{id}")
    public String permit(@PathVariable Long id, @PathVariable Long permit){
        messageService.changeById(id,permit);
        return "redirect:/message/list";
    }

}
