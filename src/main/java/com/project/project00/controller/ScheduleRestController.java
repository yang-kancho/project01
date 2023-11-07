package com.project.project00.controller;

import com.project.project00.entity.ScheduleList;
import com.project.project00.entity.ScheduleMemberList;
import com.project.project00.entity.ScheduleParticipant;
import com.project.project00.repository.ScheduleParticipantRepository;
import com.project.project00.service.ScheduleListService;
import com.project.project00.service.ScheduleMemberListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ScheduleRestController {

    @Autowired
    private ScheduleListService ScheduleListService;

    @Autowired
    private ScheduleParticipantRepository participantRepository;

    @Autowired
    private ScheduleMemberListService scheduleMemberListService;

    //스케줄정보 가져오기
    @GetMapping("/schedules/{id}")
    @ResponseBody
    public Optional<ScheduleList> getSchedule(@PathVariable Long id) {
        return ScheduleListService.getScheduleById(id);
    }


    //삭제기능
    @DeleteMapping("/scheduleDelete/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        participantRepository.deleteSchedule(id);
        ScheduleListService.deleteSchedule(id);
        scheduleMemberListService.deleteSchedule(id);
    }

//수정기능
//    @PutMapping("/schedulesUpdate/{id}")
//    public ScheduleList updateSchedule(@PathVariable Long id, @RequestBody ScheduleList updatedSchedule) {
//        return ScheduleListService.updateSchedule(id, updatedSchedule);
//    }


//    @GetMapping("/schedule")
//    public List<ScheduleList> getSchedules(HttpSession session) {
//        Long id = (Long) session.getAttribute("userId");
//System.out.println(id);
//        return ScheduleListService.getFutureSchedules(id);
//    }

//    @GetMapping("/schedule")
//    @ResponseBody
//    public List<ScheduleList> getAllSchedules() {
//        return ScheduleListService.getAllSchedulesList();
//    }


    //    @GetMapping("/schedules")
//    public List<ScheduleList> getSchedules() {
//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis()); // 현재 시간
//        List<ScheduleList> allSchedules = ScheduleListService.getAllSchedulesList();
//
//        //
//        allSchedules = allSchedules.stream()
//                .filter(schedule -> schedule.getDatetime().after(currentTimestamp))
//                .collect(Collectors.toList());
//
//        return allSchedules;
//    }
// 참여 버튼을 누를 때 실행되는 메서드
    @PostMapping("/joinSchedule/{id}")
    public ResponseEntity<String> joinSchedule(@PathVariable Long id, @RequestParam String nickname,
                                               @SessionAttribute(name = "userId", required = false) Long userId) {
        Optional<ScheduleList> optionalSchedule = ScheduleListService.getScheduleById(id);
        if (optionalSchedule.isPresent()) {
            ScheduleList schedule = optionalSchedule.get();
            if (scheduleMemberListService.findByuserIdAndScheduleId(userId, schedule.getId())) {
                ScheduleParticipant participant = new ScheduleParticipant();
                participant.setSchedule(schedule);
                participant.setNickname(nickname);
                ScheduleMemberList member = new ScheduleMemberList();
                member.setUserId(userId);
                member.setScheduleId(schedule.getId());
                member.setAdminId(schedule.getAdmin());
                member.setLat(schedule.getLat());
                member.setLng(schedule.getLng());
                member.setScheduleTitle(schedule.getScheduleTitle());
                scheduleMemberListService.createScheduleMember(member);
                participantRepository.save(participant);


                return ResponseEntity.ok("참여하셨습니다.");
            } else return ResponseEntity.ok("이미 참여하셨습니다.");
        }
        return ResponseEntity.badRequest().body("참여에 실패했습니다.");
    }

    // 참여자 목록을 가져오는 메서드
    @GetMapping("/getParticipants/{id}")
    public ResponseEntity<List<ScheduleParticipant>> getParticipants(@PathVariable Long id) {
        List<ScheduleParticipant> participants = participantRepository.findByScheduleId(id);
        return ResponseEntity.ok(participants);
    }

    //나가기 버튼누르면
    @DeleteMapping("/leaveSchedule/{id}/{nickname}")
    public ResponseEntity<String> leaveSchedule(@PathVariable Long id, @PathVariable String nickname,
                                                @SessionAttribute(name = "userId", required = false) Long userId) {
        try {
            ScheduleListService.leaveSchedule(id, nickname);
            scheduleMemberListService.leaveSchedule(userId, id);
            return ResponseEntity.ok("스케줄에서 나갔습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("나가기 실패: " + e.getMessage());
        }
    }

    @GetMapping("/myScheduleViewById")
    public List<ScheduleList> myScheduleViewById(@RequestParam("id") Long userId) {
        Date currentTime = new Date();

        List<Long> scheduleList = scheduleMemberListService.findByIdToScheduleId(userId);
        List<ScheduleList> list = new ArrayList<>();

        for (Long i : scheduleList) {
            ScheduleList schedule = ScheduleListService.findById(i);
            if (schedule.getDatetime().after(currentTime)) {
                list.add(schedule);
            }
        }

        Comparator<ScheduleList> scheduleComparator = Comparator.comparing(ScheduleList::getDatetime);
        Collections.sort(list, scheduleComparator);

        return list;
    }

    @GetMapping("/schedulePasswordCheck")
    public String boardPasswordCheck(@RequestParam("scheduleId")Long scheduleId,
                                     @RequestParam("userId")Long userId){
        if(scheduleMemberListService.secretMemberCheck(scheduleId, userId) > 0)return "-1";
        else return ScheduleListService.passwordCheck(scheduleId);
    }





}


