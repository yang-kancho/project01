package com.project.project00.controller;


import com.project.project00.entity.ScheduleList;
import com.project.project00.entity.ScheduleMemberList;
import com.project.project00.entity.ScheduleParticipant;
import com.project.project00.repository.ScheduleParticipantRepository;
import com.project.project00.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScheduleController {


    @Autowired
    private ScheduleListService ScheduleListService;


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private BoardListService boardListService;

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private ScheduleMemberListService scheduleMemberListService;

    @Autowired
    private ScheduleParticipantRepository participantRepository;



////////      스케줄          ////////////////

    //메인에서 주소받고 스케줄 맵으로
//    @GetMapping("/scheduleMap")
//    public String scheduleMap(Model model, HttpSession session, HttpServletRequest request) {
//        Long id = (Long) session.getAttribute("userId");
//        System.out.println("유저아이디= "+ScheduleListService.getFutureSchedules(id));
//
//
//
//        model.addAttribute("schedule",ScheduleListService.getFutureSchedules(id));
//        model.addAttribute("userId",session.getAttribute("userId"));
//        model.addAttribute("nickName",session.getAttribute("nickname"));
//
//        return "scheduleMap";
//    }


    @GetMapping("/scheduleMap")
    public String scheduleMap(Model model, HttpSession session, HttpServletRequest request) {
        Long userId = (Long) session.getAttribute("userId");
        int secret = 1; // 기본적으로 공개로 설정

        if (session.getAttribute("userId") == null || session.getAttribute("userId") == "") {
            return "loginForm";
        }

        List<ScheduleList> schedules = ScheduleListService.getFutureSchedules();
        model.addAttribute("schedule", schedules);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("nickName", session.getAttribute("nickname"));

        return "scheduleMap";
    }

    // 전체스케줄 ScheduleListAll()   시간지난스케줄 제외 getFutureSchedules()

    @PostMapping("/schedule/writeSchedule")
    public String writeschedule(ScheduleList schedule) {
        System.out.println(schedule);

        ScheduleListService.createSchedule(schedule);

        ScheduleMemberList member = new ScheduleMemberList();
        member.setScheduleId(schedule.getId());
        member.setAdminId(schedule.getAdmin());
        member.setUserId(schedule.getAdmin());
        member.setLat(schedule.getLat());
        member.setLng(schedule.getLng());
        member.setScheduleTitle(schedule.getScheduleTitle());
        String nickname = userInfoService.findNicknameById(schedule.getAdmin());
        ScheduleParticipant participant = new ScheduleParticipant();
        participant.setSchedule(schedule);
        participant.setNickname(nickname);
        participantRepository.save(participant);
        scheduleMemberListService.createScheduleMember(member);


        return "redirect:/scheduleMap";
    }
}

