package com.sm130.meeting.service;

import com.sm130.meeting.dao.DetailRepository;
import com.sm130.meeting.po.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    public Page<Detail> listByUserId(Pageable pageable, Long userId) {
        Detail detail = new Detail();
        detail.setUserid(userId);
        Example<Detail> of = Example.of(detail);
        return detailRepository.findAll(of,pageable);
    }

    @Override
    public Page<Detail> list(Pageable pageable) {
        Detail detail = new Detail();
        return detailRepository.findAll(pageable);
    }
}
