package com.sm130.meeting.service;

import com.sm130.meeting.dao.DetailRepository;
import com.sm130.meeting.po.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailRepository detailRepository;

    @Override
    public Detail save(Detail detail) {
        Detail resultSave = detailRepository.save(detail);
        return resultSave;
    }

    @Override
    public Page<Detail> list(Pageable pageable) {
        return detailRepository.findAll(pageable);
    }
}
