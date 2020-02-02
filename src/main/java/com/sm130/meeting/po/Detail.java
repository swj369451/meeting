package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_detail")
@Data
public class Detail {

    @Id
    @GeneratedValue
    private Long id;

//    用户昵称
    private Long userid;
    //   昵称
    private String nickname;

//    操作时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    //    操作内容内容
    private String content;



}
