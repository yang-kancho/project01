package com.project.project00.controller;

import com.project.project00.entity.ChatContent;
import com.project.project00.entity.ChatRoomInfo;
import com.project.project00.entity.ChatRoomMember;
import com.project.project00.service.ChatContentService;
import com.project.project00.service.ChatRoomListService;
import com.project.project00.service.ChatRoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChatRoomController {

    @Autowired
    private ChatRoomListService chatRoomListService;

    @Autowired
    private ChatRoomMemberService chatRoomMemberService;

    @Autowired
    private ChatContentService chatContentService;


    @GetMapping("/chatMap")
    public String chatMap(Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("chat", chatRoomListService.chatRoomListAll());

        if (session.getAttribute("userId") == null || session.getAttribute("userId") == "") {
            return "loginForm";
        }
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("nickName", session.getAttribute("nickname"));

        return "chatMapMain";
    }

    @PostMapping("/chat/makeRoom")
    public String chatRoomCreate(ChatRoomInfo chatRoom) {

        chatRoomListService.createChatRoom(chatRoom);

        return "redirect:/chatMap";

    }

    @GetMapping("/chatroom/chatwindow")
    public String chatRoomWindow(@RequestParam("roomId") Long roomId,
                                 Model model, HttpSession session) {

        ChatRoomInfo room = chatRoomListService.findById(roomId);
        model.addAttribute("roomInfo", room);
        Long userId = (Long) session.getAttribute("userId");
        String userNick = session.getAttribute("nickname").toString();
        model.addAttribute("userId", userId);
        model.addAttribute("userNick", userNick);
        ChatRoomMember join = new ChatRoomMember();
        join.setRoomId(roomId);
        join.setUserId(userId);
        Long memberIntoId = chatRoomMemberService.intoUser(join);
        Long loadStartId = chatRoomMemberService.findToStartId(roomId, userId);
        if (loadStartId == null) {
            ChatContent sysMsg = new ChatContent();
            sysMsg.setWriterId(userId);
            sysMsg.setWriterName("시스템 메시지");
            sysMsg.setRoomId(roomId);
            sysMsg.setMessage(userNick + "님이 입장하셨습니다.");
            chatContentService.sendSystemMessage(sysMsg, memberIntoId);
            loadStartId = chatRoomMemberService.findToStartId(roomId, userId);
        }
        List<ChatContent> list = chatContentService.firstJoinLoadMessage(roomId, loadStartId);
        if ((list != null) && (!list.isEmpty())) {
            chatRoomMemberService.UpdateReadChatId(list.get(list.size() - 1).getId(),roomId,userId);
        }
        model.addAttribute("firstChatList", list);
        return "chatroom.html";
    }
}
