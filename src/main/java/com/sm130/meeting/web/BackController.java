package com.sm130.meeting.web;

import com.sm130.meeting.po.User;
import com.sm130.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("back")
public class BackController {
    @Autowired
    private UserService userService;

    @Autowired
    private BackUserController backUserController;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "b/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("username","");
        return "b/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password, Model model, HttpSession session){
        User user = userService.checkUser(username, password);
        if(!"root".equals(username) || user ==null){
            model.addAttribute("message","账号或密码错误");
            model.addAttribute("username",username);
            return "b/login";

        }
        session.setAttribute("user",user);
//        return index();
        return backUserController.list(model);

    }


}
