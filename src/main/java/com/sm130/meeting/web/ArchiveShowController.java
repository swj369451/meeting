package com.sm130.meeting.web;

import com.sm130.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ArchiveShowController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", meetingService.archiveMeeting());
        model.addAttribute("meetingCount", meetingService.countMeeting());
        return "archives";
    }
}
