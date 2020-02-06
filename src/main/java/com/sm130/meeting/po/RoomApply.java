package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_roomaply")
public class RoomApply {

    @Id
    @GeneratedValue
    private Long id;

//    会议室内容
    private String content;

//    申请时间
    private Date time;


//    申请人Id
    private Long userId;

//    是否通过
    private Boolean permission;

//  会议申请人
    @Transient
    private User user;
}
