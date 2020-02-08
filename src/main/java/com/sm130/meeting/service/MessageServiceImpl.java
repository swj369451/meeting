package com.sm130.meeting.service;

import com.sm130.meeting.dao.MessageRepository;
import com.sm130.meeting.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Page<Message> listMeeting(Pageable pageable, Long receive) {
        Message message = new Message();
        message.setReceiveId(receive);
        Example<Message> of = Example.of(message);
        return messageRepository.findAll(of,pageable);
    }

    @Override
    public void changeById(Long id, Long permit) {
        Message message = messageRepository.getOne(id);
        message.setResult(permit);
        messageRepository.save(message);
    }


}
