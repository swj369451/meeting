package com.sm130.meeting.service;

import com.sm130.meeting.po.Comment;
import com.sm130.meeting.po.Meeting;

import java.util.List;

/**
 * Created by limi on 2017/10/22.
 */
public interface CommentService {

    List<Comment> listCommentByMeetingId(Long blogId);

    Comment saveComment(Comment comment);

    void deleteByMeeting(Meeting meeting);
}
