package com.sm130.meeting.vo;

import lombok.Data;

@Data
public class MeetingQuery {

    private String title;
    private Long typeId;
    private boolean recommend;
}
