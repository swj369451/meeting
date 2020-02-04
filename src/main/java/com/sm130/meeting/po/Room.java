package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_room")
public class Room {

    @Id
    @GeneratedValue
    private Long id;

//    会议室名称
    private String name;

//    会议室简介
    private String description;

//    会议室地址
    private String place;

//    可容纳人数
    private Long number;
//          1   0~10人
//          2   11~50人
//          3             51~100人
//          4            101~150人
//          5          150~200人
//          6          200人以上

//    会议室图片地址
    private String picture;

//    投影仪
    private Boolean projector;
}
