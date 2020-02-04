package com.sm130.meeting;

import com.sm130.meeting.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
//@WebAppConfiguration指定加载 ApplicationContext
@WebAppConfiguration
class MeetingApplicationTests {

    @Autowired
    private RoomService roomService;

    @Test
    void contextLoads() {
    }


}
