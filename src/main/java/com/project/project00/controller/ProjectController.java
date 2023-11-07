package com.project.project00.controller;


import com.project.project00.entity.BoardList;
import com.project.project00.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProjectController {

    @Autowired
    private BoardListService boardListService;

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private BoardContentService boardContentService;

    @Autowired
    private BoardVisitHistoryService boardVisitHistoryService;

    @Autowired
    private FriendListService friendListService;

    @GetMapping("/indexPage")
    public String mainView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if(session.getAttribute("userId")== null || session.getAttribute("userId") == ""){
            return "loginForm";
        }

        return "index";
    }

    @PostMapping("/writeBoard")
    public String writeBoard(BoardList board) {


        boardListService.createBoard(board);

        return "redirect:/boardMap";
    }


    @GetMapping("/boardMap")
    public String boardMap(Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("board",boardListService.boardListAll());

        if(session.getAttribute("userId")== null || session.getAttribute("userId") == ""){
            return "loginForm";
        }
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("nickName", session.getAttribute("nickname"));

        return "boardMapMain";
    }

    @GetMapping("/boardDetail")
    public String boardViewTest(){
        return "boardDetail";
    }


    @GetMapping("/userMenu/userwindow")
    public String userWindow(HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("userId");


        model.addAttribute("userId",userId);



        return "messageForm";
    }

}
