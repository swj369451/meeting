package com.sm130.meeting.web.admin;

import com.sm130.meeting.po.Meeting;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.MeetingService;
import com.sm130.meeting.service.RoomApplyService;
import com.sm130.meeting.service.TagService;
import com.sm130.meeting.service.TypeService;
import com.sm130.meeting.vo.MeetingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping("/admin")
public class MeetingController {

    private static final String INPUT = "admin/meetings-input";
    private static final String LIST = "admin/meetings";
    private static final String REDIRECT_LIST = "redirect:/admin/meetings";

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @Autowired
    private RoomApplyService roomApplyService;

    /**
     * 获取所有会议
     * @param pageable
     * @param meeting
     * @param model
     * @return
     */
    @GetMapping("/meetings")
    public String meetings(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                           MeetingQuery meeting, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", meetingService.listMeeting(pageable, meeting));
        return LIST;
    }

    /**
     * 会议搜索
     * @param pageable
     * @param meeting
     * @param model
     * @return
     */
    @PostMapping("/meetings/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         MeetingQuery meeting, Model model) {
        model.addAttribute("page", meetingService.listMeeting(pageable, meeting));
        return "admin/meetings :: meetingList";
    }


    /**
     * 会议添加跳转页面
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/meetings/input")
    public String input(Model model, HttpSession session) {
        setTypeAndTag(model);
        User user = (User) session.getAttribute("user");

        model.addAttribute("meeting", new Meeting());

        model.addAttribute("rooms",roomApplyService.validRoom(user.getId()));

        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }


    /**
     * 编辑会议
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/meetings/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Meeting meeting = meetingService.getMeeting(id);
        meeting.init();
        model.addAttribute("meeting",meeting);
        model.addAttribute("h",meeting.getTime().getHours());
        model.addAttribute("m",meeting.getTime().getMinutes());
        return INPUT;
    }



    @PostMapping("/meetings")
    public String post(Meeting meeting, RedirectAttributes attributes, HttpSession session,int h,int m) {
        meeting.setUser((User) session.getAttribute("user"));
        meeting.setType(typeService.getType(meeting.getType().getId()));
        meeting.setTags(tagService.listTag(meeting.getTagIds()));

//        判断是否是有申请的会议室

        if(meeting.getPlace().substring(0,3).equals("会议室")){
            meeting.setPlace(meeting.getPlace().split("，")[1].substring(3));
        }
        Meeting b;
        if (meeting.getId() == null) {
            String[] split = meeting.getTimes().split("-");
            Date time = new Date(Integer.parseInt(split[0])-1900,Integer.parseInt(split[1]),Integer.parseInt(split[2]),h,m);
            meeting.setTime(time);
            if(meeting.getFirstPicture()==null){
                meeting.setFirstPicture("https://i.picsum.photos/id/167/728/485.jpg");
            }
            b =  meetingService.saveMeeting(meeting);
        } else {
            String[] split = meeting.getTimes().split("-");
            Date time = new Date(Integer.parseInt(split[0])-1900,Integer.parseInt(split[1]),Integer.parseInt(split[2]),h,m);
            meeting.setTime(time);
            b = meetingService.updateMeeting(meeting.getId(), meeting);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/meetings/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        meetingService.deleteMeeting(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }



}
