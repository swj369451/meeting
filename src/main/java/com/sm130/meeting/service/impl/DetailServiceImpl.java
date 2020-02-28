package com.sm130.meeting.service.impl;

import com.sm130.meeting.dao.DetailRepository;
import com.sm130.meeting.po.Detail;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.util.DetailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailRepository detailRepository;

    @Override
    public Detail save(String content) {
        HttpSession session = DetailUtils.getSession();
        User user = (User) session.getAttribute("user");
        Detail detail = new Detail(null, user.getId(), user.getNickname(), user.getType(), new Date(), user.getNickname() + ":" + content);
        Detail resultSave = detailRepository.save(detail);
        // todo 还有部分模块没有添加
        return resultSave;
    }

    @Override
    public Page<Detail> listPageByUserId(Pageable pageable, Long userId) {
        Detail detail = new Detail();
        detail.setUserid(userId);
        Example<Detail> of = Example.of(detail);
        return detailRepository.findAll(of, pageable);
    }

    @Override
    public List<Detail> listByUserId(Long userId) {
        Detail detail = new Detail();
        detail.setUserid(userId);
        Example<Detail> of = Example.of(detail);
        return detailRepository.findAll(of);
    }

    @Override
    public Page<Detail> list(Pageable pageable) {
        Detail detail = new Detail();
        return detailRepository.findAll(pageable);
    }
}
