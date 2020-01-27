package com.sm130.meeting.service;


import com.sm130.meeting.NotFoundException;
import com.sm130.meeting.dao.MeetingRepository;
import com.sm130.meeting.po.Meeting;
import com.sm130.meeting.po.Type;
import com.sm130.meeting.util.MarkdownUtils;
import com.sm130.meeting.util.MyBeanUtils;
import com.sm130.meeting.vo.MeetingQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MeetingServiceImpl implements MeetingService {


    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private CommentService commentService;

    @Override
    public Meeting getMeeting(Long id) {
        return meetingRepository.getOne(id);
    }

    @Transactional
    @Override
    public Meeting getAndConvert(Long id) {
        Meeting meeting = meetingRepository.getOne(id);
        if (meeting == null) {
            throw new NotFoundException("该博客不存在");
        }
        Meeting b = new Meeting();
        BeanUtils.copyProperties(meeting,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        meetingRepository.updateViews(id);
        return b;
    }


    @Override
    public Page<Meeting> listMeeting(Pageable pageable, MeetingQuery meeting) {
        return meetingRepository.findAll(new Specification<Meeting>() {
            @Override
//                                       获取到表字段的属性值，查询的一个容器可以把条件放这里面，设置具体时间的表达式
            public Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(meeting.getTitle()) && meeting.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+meeting.getTitle()+"%"));
                }
                if (meeting.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), meeting.getTypeId()));
                }
                if (meeting.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), meeting.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Meeting> listMeeting(Pageable pageable) {
        Page<Meeting> list = meetingRepository.findAll(new Specification<Meeting>() {

            @Override
            public Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
//                判断是否发布
                p = cb.and(p, cb.equal(root.get("published"), true));

                return p;
            }
        }, pageable);
        return list;
    }

    @Override
    public Page<Meeting> listMeeting(Long tagId, Pageable pageable) {
        return meetingRepository.findAll(new Specification<Meeting>() {
            @Override
            public Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Meeting> listMeeting(String query, Pageable pageable) {
        return meetingRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Meeting> listRecommendMeetingTop(Integer size) {
//        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
//        Pageable pageable = new PageRequest(0, size, sort);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable= PageRequest.of(0, size, sort);
        List<Meeting> list = meetingRepository.findTop(pageable).stream().filter((item) -> {
            if (item.isPublished()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public Map<String, List<Meeting>> archiveMeeting() {
        List<String> years = meetingRepository.findGroupYear();
        Map<String, List<Meeting>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, meetingRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countMeeting() {
        return meetingRepository.count();
    }


    @Transactional
    @Override
    public Meeting saveMeeting(Meeting meeting) {
        if (meeting.getId() == null) {
            meeting.setCreateTime(new Date());
            meeting.setUpdateTime(new Date());
            meeting.setViews(0);
        } else {
            meeting.setUpdateTime(new Date());
        }
        return meetingRepository.save(meeting);
    }

    @Transactional
    @Override
    public Meeting updateMeeting(Long id, Meeting meeting) {
        Meeting b = meetingRepository.getOne(id);
        if (b == null) {
            throw new NotFoundException("该会议不存在");
        }
        BeanUtils.copyProperties(meeting,b, MyBeanUtils.getNullPropertyNames(meeting));
        b.setUpdateTime(new Date());
        return meetingRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).get();
        commentService.deleteByMeeting(meeting);
        meetingRepository.deleteById(id);
    }
}
