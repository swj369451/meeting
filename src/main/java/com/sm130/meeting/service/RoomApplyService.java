package com.sm130.meeting.service;

import com.sm130.meeting.po.RoomApply;

import java.util.List;

public interface RoomApplyService {
    void save(RoomApply roomApply);

    List<RoomApply> list();
}
