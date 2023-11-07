package com.project.project00.service;


import com.project.project00.entity.ScheduleList;
import com.project.project00.entity.ScheduleParticipant;
import com.project.project00.repository.ScheduleListRepository;
import com.project.project00.repository.ScheduleMemberListRepository;
import com.project.project00.repository.ScheduleParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleListService {

    @Autowired
    private ScheduleListRepository ScheduleListRepository;

    @Autowired
    private ScheduleParticipantRepository participantRepository;

    @Autowired
    private ScheduleMemberListRepository scheduleMemberListRepository;
    @Transactional
    public ScheduleList createSchedule(ScheduleList schedule) {
        ScheduleListRepository.save(schedule);

        return schedule;
    }

    //데이터 베이스에서 현재시간하고 비교해서 시간지난 스케줄 제외 시크릿값이1이면 공개 0 이면 작성자만보이도록 가져옴
    @Transactional
    public List<ScheduleList> getFutureSchedules() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return ScheduleListRepository.findFutureSchedules(currentTimestamp);
    }

    public Optional<ScheduleList> getScheduleById(Long id) {
        return ScheduleListRepository.findById(id);
    }

    public void deleteSchedule(Long id) {
        ScheduleListRepository.deleteById(id);
    }

    @Transactional
    public void leaveSchedule(Long scheduleId, String nickname) {
        List<ScheduleParticipant> participants = participantRepository.findByScheduleIdAndNickname(scheduleId, nickname);
        if (!participants.isEmpty()) {
            for (ScheduleParticipant participant : participants) {
                participantRepository.delete(participant);

            }
        } else {
            throw new IllegalArgumentException("해당 참여자가 존재하지 않습니다.");
        }
    }

    public ScheduleList findById(Long id) {
        return ScheduleListRepository.findById(id).get();
    }

    public String passwordCheck(Long scheduleId){
        return ScheduleListRepository.passwordCheck(scheduleId);
    }




}

//전체스케줄 가져오기
//    @Transactional
//    public List<ScheduleList> ScheduleListAll(){
//        return ScheduleListRepository.findAll();
//    }




//    public ScheduleList updateSchedule(Long id, ScheduleList updatedSchedule) {
//        Optional<ScheduleList> optionalSchedule = ScheduleListRepository.findById(id);
//        if (optionalSchedule.isPresent()) {
//            ScheduleList existingSchedule = optionalSchedule.get();
//            existingSchedule.setScheduleTitle(updatedSchedule.getScheduleTitle());
//            existingSchedule.setContent(updatedSchedule.getContent());
//            // 다른 필드들 업데이트
//            return ScheduleListRepository.save(existingSchedule);
//        } else {
//            return null; // 스케줄이 없음
//        }
//    }



