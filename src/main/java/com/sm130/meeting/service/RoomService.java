package com.sm130.meeting.service;

import com.sm130.meeting.po.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAll();

    List<Room> getAllBySearch(String title,String typeId);
}
