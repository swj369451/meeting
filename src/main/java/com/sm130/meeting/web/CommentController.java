package com.sm130.meeting.web;

import com.sm130.meeting.po.Comment;
import com.sm130.meeting.po.Meeting;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.CommentService;
import com.sm130.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MeetingService meetingService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{meetingId}")
    public String comments(@PathVariable Long meetingId, Model model) {
        model.addAttribute("comments", commentService.listCommentByMeetingId(meetingId));
        return "meeting :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long meetingId = comment.getMeeting().getId();
        Meeting meeting = meetingService.getMeeting(meetingId);
        comment.setMeeting(meeting);
        User user = (User) session.getAttribute("user");

        if (user.getId()==meeting.getUser().getId()) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + meetingId;
    }



}
