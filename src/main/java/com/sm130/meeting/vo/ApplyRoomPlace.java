package com.sm130.meeting.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyRoomPlace {

    //    会议室名称
    private String name;

    //    会议室地址
    private String place;


    //    到期时间
    private Date time;
}
