package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;
//   昵称
    private String nickname;
//    账号
    private String username;
//    密码
    private String password;
//    邮箱
    private String email;
//    头像
    private String avatar;
//    类型
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user")
    private List<Meeting> meetings = new ArrayList<>();


}
