package com.project.project00.service;

import com.project.project00.entity.ScheduleMemberList;
import com.project.project00.repository.ScheduleMemberListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleMemberListService {
    @Autowired
    ScheduleMemberListRepository scheduleMemberListRepository;


    public void createScheduleMember(ScheduleMemberList member){
        scheduleMemberListRepository.save(member);
    }

    public void deleteSchedule(Long id){
        scheduleMemberListRepository.scheduleDelete(id);
    }

    public Boolean findByuserIdAndScheduleId(Long userId, Long scheduleId){
        if(scheduleMemberListRepository.findByuserIdAndScheduleId(userId,scheduleId)>0){
            return false;
        }else{
            return true;
        }
    }

    public List<Long> findByIdToScheduleId(Long userId){
        return scheduleMemberListRepository.findByIdToScheduleId(userId);
    }

    public void leaveSchedule(Long userId, Long scheduleId){
        scheduleMemberListRepository.leaveSchedule(userId,scheduleId);
    }

    public int secretMemberCheck(Long scheduleId, Long userId){
        return scheduleMemberListRepository.secretHistoryCheck(scheduleId,userId);
    }

}
