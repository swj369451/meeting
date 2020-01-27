package com.sm130.meeting.dao;

import com.sm130.meeting.po.Comment;
import com.sm130.meeting.po.Meeting;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long>{


    List<Comment> findByMeetingIdAndParentCommentNull(Long blogId, Sort sort);

    void deleteByMeeting(Meeting meeting);
}
