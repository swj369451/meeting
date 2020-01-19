package com.sm130.meeting.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_meeting")
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;
//   会议名称
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
//    会议内容
    private String content;
//    会议头图片
    private String firstPicture;
//    标记
    private String flag;
//    游览次数
    private Integer views;
//    赞赏是否开启
    private boolean appreciation;
//    版权信息是否开启
    private boolean shareStatement;
//    评论是否开启
    private boolean commentabled;
//    是否发布
    private boolean published;
//    是否推荐
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
//    创建时间
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
//    更新时间
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();


    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "meeting")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    private String description;
}
