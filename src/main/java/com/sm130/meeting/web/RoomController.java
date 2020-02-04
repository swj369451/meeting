package com.sm130.meeting.web;

import com.sm130.meeting.po.Room;
import com.sm130.meeting.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public String about(Model model) {
        model.addAttribute("rooms",roomService.getAll());
        return "room";
    }

    @PostMapping("/search")
    public String search(String typeId,String title, Model model) {
        List<Room> allBySearch = roomService.getAllBySearch(title, typeId);
        model.addAttribute("rooms",allBySearch);
//        这里返回的是html的名称room.html
        return "room :: rootList";
    }
}
