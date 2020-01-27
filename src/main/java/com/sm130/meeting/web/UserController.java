package com.sm130.meeting.web;

import com.sm130.meeting.po.User;
import com.sm130.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @GetMapping("/users")
    public String users(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page",userService.listUsers(pageable));
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String add() {
        return "admin/users-add";
    }


//    编辑用户
    @GetMapping("/users/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
//        model.addAttribute("tag", tagService.getTag(id));
        model.addAttribute("user",userService.findUser(id));
        return "admin/users-input";
    }

    @PostMapping("/users/adduser")
    public String adduser(@Valid User user) {
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setAvatar("https://unsplash.it/800/600?image=1005");
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}")
    public String editPost(@Valid User save, @PathVariable Long id) {
        User user = userService.findUser(id);
        user.setNickname(save.getNickname());
        user.setUsername(save.getUsername());
        user.setPassword(save.getPassword());
        user.setType(save.getType());
        user.setUpdateTime(new Date());
        User result = userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable Long id, RedirectAttributes attributes) {
//        tagService.deleteTag(id);
        userService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/users";
    }

}
