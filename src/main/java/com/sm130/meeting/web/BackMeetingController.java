package com.sm130.meeting.web;

import com.sm130.meeting.po.Meeting;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.service.MeetingService;
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
import java.util.List;

@Controller
@RequestMapping("back/meeting")
public class BackMeetingController {

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private MeetingService meetingService;

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
        List<Meeting> meetings = meetingService.getAll();
        model.addAttribute("meetings",meetings);
        return "b/meetingList";
    }

    /**
     * 跳转到用户表单页面
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String meetingForm(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        if(id!=null){
            Meeting meeting = meetingService.getMeeting(Long.parseLong(id));
            model.addAttribute("meeting",meeting);
        }
        return "b/meeting_form";
    }

    /**
     * 保存与新增会议
     * @param meeting
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Meeting meeting,
                       Model model,
                       RedirectAttributes redirectAttributes){
        BaseResult baseResult = BaseResult.success("更新成功");
        Meeting save = meetingService.getMeeting(meeting.getId());
        save.setShareStatement(meeting.isShareStatement());
        save.setCommentabled(meeting.isCommentabled());
        save.setPublished(meeting.isPublished());
        save.setRecommend(meeting.isRecommend());
        meetingService.saveMeeting(save);
        if(save != null){
//            保存成功
            redirectAttributes.addFlashAttribute("result",baseResult);
            return "redirect:/back/meeting/list";
        }else{
//            保存失败
            baseResult = BaseResult.success("更新失败");
            model.addAttribute("result",baseResult);
            model.addAttribute("meeting",save);
            return "b/meeting_form";
        }
    }

    /**
     * 删除用户
     * @param meetingId
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResult delete(String meetingId){
        meetingService.deleteMeeting(Long.parseLong(meetingId));
        BaseResult baseResult;
        baseResult=BaseResult.success("删除成功");
        return baseResult;
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(String id,Model model){
        Meeting meeting = meetingService.getMeeting(Long.parseLong(id));
        model.addAttribute("meeting",meeting);
        return "meetingDetail";
    }
}
