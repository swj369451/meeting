package com.sm130.meeting.web;

import com.sm130.meeting.po.User;
import com.sm130.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public String test(User user, Model model,HttpSession session) {
        User user1 = userService.findUser(user.getId());
        user1.setNickname(user.getNickname());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user.setUpdateTime(new Date());


//        保存到数据库
        User save = userService.save(user1);
        //       跟新session的值
        session.setAttribute("user",user1);
        model.addAttribute("user",save);
        return "about";
    }
}
