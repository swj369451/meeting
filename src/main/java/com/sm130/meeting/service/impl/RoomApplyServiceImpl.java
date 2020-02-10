package com.sm130.meeting.service.impl;

import com.sm130.meeting.dao.RoomApplyRepository;
import com.sm130.meeting.dao.RoomRepository;
import com.sm130.meeting.po.Room;
import com.sm130.meeting.po.RoomApply;
import com.sm130.meeting.service.RoomApplyService;
import com.sm130.meeting.vo.ApplyRoomPlace;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class RoomApplyServiceImpl implements RoomApplyService {

    @Autowired
    private RoomApplyRepository roomApplyRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void save(RoomApply roomApply) {
        roomApplyRepository.save(roomApply);
    }

    @Override
    public List<RoomApply> list() {
        return roomApplyRepository.findAll();
    }

    @Override
    public RoomApply findById(Long roomApplyId) {
        return roomApplyRepository.findById(roomApplyId).get();
    }

    @Override
    public List<ApplyRoomPlace> validRoom(Long userId) {
        RoomApply roomApply = new RoomApply();
        roomApply.setApplyUserId(userId);
        roomApply.setPermission(1L);

        Example<RoomApply> example = Example.of(roomApply);
        List<RoomApply> List = roomApplyRepository.findAll(example);

        java.util.List<ApplyRoomPlace> collect = List.stream().filter((o) -> {//查询有效时间
            if (o.getTime().compareTo(new Date()) > 0) {
                return true;
            }
            return false;
        }).map((o) -> {//查询出会议室
            ApplyRoomPlace applyRoomPlace = new ApplyRoomPlace();
            Room room = roomRepository.findById(o.getRoomId()).get();
            BeanUtils.copyProperties(room,applyRoomPlace);
            applyRoomPlace.setTime(o.getTime());
            return applyRoomPlace;
        }).collect(Collectors.toList());
        return collect;
    }
}
