package com.sm130.meeting.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_message")
@AllArgsConstructor()
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private Long id;

//    消息发送方
    private Long requireId;

//    消息接收方
    private Long receiveId;

//    消息内容
    private String content;

//    消息处理结果0未处理，1允许，2拒绝
    private Long result;

//    消息类型，0会议室申请
    private Long type;

    private Date createTime;

//    设置关联的表
    private Long relate;
}
