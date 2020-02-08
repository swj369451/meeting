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
    private Long applyUserId;

//    会议室Id
    private Long roomId;

//    管理员id
    private Long managerId;

//    是否通过0没决定，1通过，2不通过
    private Integer permission;
}
