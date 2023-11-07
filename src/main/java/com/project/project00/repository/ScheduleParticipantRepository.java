package com.project.project00.repository;

import com.project.project00.entity.ScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface    ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
    List<ScheduleParticipant> findByScheduleId(Long scheduleId);

    List<ScheduleParticipant> findByScheduleIdAndNickname(Long scheduleId, String nickname);


    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM schedule_participants WHERE schedule_id = :scheduleId", nativeQuery = true)
    @Transactional
    void deleteSchedule(@Param("scheduleId") Long scheduleId);
}
