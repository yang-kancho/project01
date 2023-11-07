package com.project.project00.repository;

import com.project.project00.entity.ScheduleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScheduleListRepository extends JpaRepository<ScheduleList, Long> {
//    @Query("SELECT s FROM ScheduleList s WHERE s.datetime > :currentTimestamp and admin =:id")
//    List<ScheduleList> findFutureSchedules(@Param("currentTimestamp")Timestamp currentTimestamp,  @Param("id") Long id);

    //현재시간과 비교해서 시간이 지난 스케줄은 제외 시크릿값이1이면 공개 0이면 작성자만 공개
    @Query("SELECT s FROM ScheduleList s WHERE s.datetime > :currentTimestamp ")
    List<ScheduleList> findFutureSchedules(@Param("currentTimestamp") Timestamp currentTimestamp);

    @Query("SELECT password FROM ScheduleList WHERE id = :scheduleId")
    @Transactional
    String passwordCheck(@Param("scheduleId")Long scheduleId);
}

