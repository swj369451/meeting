package com.sm130.meeting.service;

import com.sm130.meeting.po.Meeting;
import com.sm130.meeting.vo.MeetingQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/20.
 */
public interface MeetingService {

    Meeting getMeeting(Long id);

    Meeting getAndConvert(Long id);

    Page<Meeting> listMeeting(Pageable pageable, MeetingQuery meeting);

    Page<Meeting> listMeeting(Pageable pageable);

    Page<Meeting> listMeeting(Long tagId, Pageable pageable);

    Page<Meeting> listMeeting(String query, Pageable pageable);

    List<Meeting> listRecommendMeetingTop(Integer size);

    Map<String,List<Meeting>> archiveMeeting();

    Long countMeeting();

    Meeting saveMeeting(Meeting meeting);

    Meeting updateMeeting(Long id, Meeting meeting);

    void deleteMeeting(Long id);
}
