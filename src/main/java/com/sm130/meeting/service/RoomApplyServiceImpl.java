package com.sm130.meeting.service;

import com.sm130.meeting.dao.RoomApplyRepository;
import com.sm130.meeting.po.RoomApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoomApplyServiceImpl implements RoomApplyService {

    @Autowired
    private RoomApplyRepository roomApplyRepository;


    @Override
    public void save(RoomApply roomApply) {
        roomApplyRepository.save(roomApply);
    }
}
