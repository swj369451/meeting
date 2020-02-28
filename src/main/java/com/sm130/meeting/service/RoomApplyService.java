package com.sm130.meeting.service;

import com.sm130.meeting.po.RoomApply;
import com.sm130.meeting.vo.ApplyRoomPlace;

import java.util.List;

public interface RoomApplyService {
    void save(RoomApply roomApply);

    List<RoomApply> list();

    RoomApply findById(Long roomApplyId);

    List<ApplyRoomPlace> validRoom(Long userId);

    List<RoomApply> findAllByRoomId(Long roomId);
}
