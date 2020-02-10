package com.sm130.meeting;

import com.sm130.meeting.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@SpringBootTest
//@WebAppConfiguration指定加载 ApplicationContext
@WebAppConfiguration
class MeetingApplicationTests {


    @Test
    void contextLoads() {
        Date date = DateUtils.String2Date("2020-02-14");
        return;
    }


}
