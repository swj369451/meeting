package com.sm130.meeting.service;

import com.sm130.meeting.po.Detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DetailService {

    Detail save(String content);
    Page<Detail> list(Pageable pageable);
    Page<Detail> listPageByUserId(Pageable pageable,Long userId);
    List<Detail> listByUserId(Long userId);
}
