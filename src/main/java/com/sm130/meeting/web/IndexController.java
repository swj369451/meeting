package com.sm130.meeting.web;


import com.sm130.meeting.service.MeetingService;
import com.sm130.meeting.service.TagService;
import com.sm130.meeting.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",meetingService.listMeeting(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendMeetings", meetingService.listRecommendMeetingTop(8));
        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", meetingService.listMeeting("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/meeting/{id}")
    public String meeting(@PathVariable Long id,Model model) {
        model.addAttribute("meeting", meetingService.getAndConvert(id));
        return "meeting";
    }

    @GetMapping("/footer/newmeeting")
    public String newmeetings(Model model) {
        model.addAttribute("newmeetings", meetingService.listRecommendMeetingTop(3));
        return "_fragments :: newmeetingList";
    }

}
