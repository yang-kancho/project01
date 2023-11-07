package com.project.project00.repository;


import com.project.project00.entity.ScheduleMemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScheduleMemberListRepository extends JpaRepository<ScheduleMemberList, Long> {



    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM schedule_member_list WHERE schedule_id = :id", nativeQuery = true)
    @Transactional
    void scheduleDelete(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM schedule_member_list WHERE user_id = :userId AND schedule_id = :scheduleId", nativeQuery = true)
    @Transactional
    int findByuserIdAndScheduleId(@Param("userId")Long userId, @Param("scheduleId")Long scheduleId);

    @Query(value = "SELECT schedule_id FROM schedule_member_list WHERE user_id = :userId", nativeQuery = true)
    @Transactional
    List<Long> findByIdToScheduleId(@Param("userId")Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM schedule_member_list WHERE schedule_id = :scheduleId AND user_id = :userId", nativeQuery = true)
    @Transactional
    void leaveSchedule(@Param("userId")Long userId, @Param("scheduleId")Long scheduleId);

    @Query(value = "SELECT count(*) FROM schedule_member_list WHERE schedule_id = :scheduleId AND user_id = :userId", nativeQuery = true)
    @Transactional
    int secretHistoryCheck(@Param("scheduleId")Long scheduleId, @Param("userId")Long userId);
}
