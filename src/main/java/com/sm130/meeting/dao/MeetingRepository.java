package com.sm130.meeting.dao;

import com.sm130.meeting.po.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by limi on 2017/10/20.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Query("select b from Meeting b where b.recommend = true")
    List<Meeting> findTop(Pageable pageable);

    @Query("select b from Meeting b where b.title like ?1 or b.content like ?1")
    Page<Meeting> findByQuery(String query, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update Meeting b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Meeting b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Meeting b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Meeting> findByYear(String year);
}
