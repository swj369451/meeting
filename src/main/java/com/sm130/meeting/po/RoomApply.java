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
    //    申请人昵称
    private String applyNickName;

//    会议室Id
    private Long roomId;

    //    会议室名称
    private String roomName;

//    管理员id
    private Long managerId;

    //    管理员姓名
    private String managerName;

//    是否通过0没决定，1通过，2不通过
    private Long permission;
}
