package com.sm130.meeting.web;


import com.sm130.meeting.po.Type;
import com.sm130.meeting.service.MeetingService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
           id = types.get(0).getId();
        }
        MeetingQuery meetingQuery = new MeetingQuery();
        meetingQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", meetingService.listMeeting(pageable, meetingQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
