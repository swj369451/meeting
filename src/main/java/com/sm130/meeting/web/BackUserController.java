package com.sm130.meeting.web;

import com.sm130.meeting.po.User;
import com.sm130.meeting.service.UserService;
import com.sm130.meeting.web.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("back/user")
public class BackUserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public User getTbUser(Long userId){
        if(userId == null){
            return new User();
        }else {
            return userService.findUser(userId);
        }

    }

    /**
     * 用户列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "b/user";
    }

    /**
     * 跳转到用户表单页面
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String userForm(Model model){
        return "b/user_form";
    }

    /**
     * 保存与新增用户
     * @param user
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(User user,
                       Model model,
                       RedirectAttributes redirectAttributes){
        User save = userService.save(user);
        BaseResult baseResult;
        if(save != null){
//            保存成功
            baseResult = BaseResult.success("新增用户成功");
            redirectAttributes.addFlashAttribute("result",baseResult);
            // // TODO: 2020/2/27 跳转地址无效
            return "redirect:back/user/list";
        }else{
//            保存失败
            baseResult = BaseResult.success("新增用户失败");
            model.addAttribute("result",baseResult);
            model.addAttribute("user",user);
            return "b/user_form";
        }
    }
}
