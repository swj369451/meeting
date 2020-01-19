package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
//    昵称
    private String nickname;
//    邮箱
    private String email;
//    内容
    private String content;
//    头像
    private String avatar;
//    创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Meeting meeting;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;

}
