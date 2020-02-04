package com.sm130.meeting.dao;

import com.sm130.meeting.po.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select b from Room b where b.name like ?1 or b.description like ?1")
    List<Room> findByQuery(String query);

}
