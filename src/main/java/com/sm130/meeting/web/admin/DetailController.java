package com.sm130.meeting.web.admin;

import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.vo.MeetingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail")
public class DetailController {

    @Autowired
    private DetailService detailService;

    @GetMapping("/list")
    public String details(@PageableDefault(size = 8, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
                           MeetingQuery meeting, Model model) {
        model.addAttribute("page",detailService.list(pageable));
        return "admin/detail";
    }

    @PostMapping("/listpage")
    public String listpage(@PageableDefault(size = 8, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
                          MeetingQuery meeting, Model model) {
        model.addAttribute("page",detailService.list(pageable));
        return "admin/detail";
    }
}
