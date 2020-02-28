package com.sm130.meeting.web.admin;

import com.sm130.meeting.po.Detail;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.service.UserService;
import com.sm130.meeting.vo.MeetingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String details(@PageableDefault(size = 8, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
                           MeetingQuery meeting, Model model) {
        model.addAttribute("page",detailService.list(pageable));
        model.addAttribute("users",userService.findAll());
        return "admin/detail";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
                         MeetingQuery meeting, Model model,Long typeId) {
        Page<Detail> list = detailService.listPageByUserId(pageable,typeId);
        model.addAttribute("page", list);

        return "admin/detail :: detailList";
    }
}
