package com.sm130.meeting.web;

import com.sm130.meeting.po.Detail;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.service.UserService;
import com.sm130.meeting.web.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("back/user")
public class BackUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

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
        return "b/userList";
    }

    /**
     * 跳转到用户表单页面
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String userForm(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        if(id!=null){
            User user = userService.findUser(Long.parseLong(id));
            model.addAttribute("user",user);
        }
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
        BaseResult baseResult;
        if(user.getId()==null){
//            新增
            baseResult = BaseResult.success("新增用户成功");
        }else{
//            修改
            baseResult = BaseResult.success("修改用户成功");
        }
        User save = userService.save(user);
        if(save != null){
//            保存成功
            redirectAttributes.addFlashAttribute("result",baseResult);
            return "redirect:/back/user/list";
        }else{
//            保存失败
            baseResult = BaseResult.success("新增用户失败");
            model.addAttribute("result",baseResult);
            model.addAttribute("user",user);
            return "b/user_form";
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResult delete(String userId){
        User user = userService.deleteById(Long.parseLong(userId));
        BaseResult baseResult;
        if(user!=null){
            baseResult=BaseResult.success("删除成功");
        }else{
            baseResult=BaseResult.fail("删除失败");
        }
        return baseResult;
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(String id,Model model){
        List<Detail> details = detailService.listByUserId(Long.parseLong(id));

        List<Detail> collect = details.stream()
                .sorted(Comparator.comparing(Detail::getTime).reversed())
                .limit(10)
                .collect(Collectors.toList());
        model.addAttribute("details",collect);
        return "b/detail";
    }
}
