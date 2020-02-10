package com.sm130.meeting.service;

import com.sm130.meeting.po.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    void save(Message message);

    Page<Message> listMeeting(Pageable pageable, Long receive);

    void changeById(Long id,Long permit);

}
