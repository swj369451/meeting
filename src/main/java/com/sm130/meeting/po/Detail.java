package com.sm130.meeting.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

    @Id
    @GeneratedValue
    private Long id;

//    用户昵称
    private Long userid;
    //   昵称
    private String nickname;

    //    类型,1是普通用户,2是管理员
    private Integer type;

//    操作时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    //    操作内容内容
    private String content;



}
