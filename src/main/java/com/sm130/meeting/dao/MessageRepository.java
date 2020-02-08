package com.sm130.meeting.dao;

import com.sm130.meeting.po.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
