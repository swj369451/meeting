package com.sm130.meeting.service.impl;

import com.sm130.meeting.dao.RoomRepository;
import com.sm130.meeting.po.Room;
import com.sm130.meeting.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;


    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAllBySearch(String title, String typeId) {
        if(typeId.equals("") && title.equals("")){
            return roomRepository.findAll();
        }
        List<Room> byQuery = roomRepository.findByQuery("%"+title+"%");
        List<Room> collect = byQuery.stream().filter((a) -> {
            if(typeId==""){
                return true;
            }else if(typeId=="1" && a.getNumber()>0 && a.getNumber()<=10){
                return true;
            }else if(typeId.equals("2")&& a.getNumber()>10 && a.getNumber()<50){
                return true;
            }else if(typeId.equals("3") && a.getNumber()>50 && a.getNumber()<100){
                return true;
            }else if(typeId.equals("4") && a.getNumber()>100 && a.getNumber()<150){
                return true;
            }else if(typeId.equals("5") && a.getNumber()>150 && a.getNumber()<200){
                return true;
            }else if(typeId.equals("6") && a.getNumber()>200){
                return true;
            }else{
                return false;
            }

        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Room getById(Long roomId) {
        return roomRepository.findById(roomId).get();
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room deleteById(Long id) {
        Room room = getById(id);
        roomRepository.deleteById(id);
        return room;
    }
}
