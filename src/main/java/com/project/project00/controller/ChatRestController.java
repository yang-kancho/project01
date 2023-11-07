package com.project.project00.controller;

import com.project.project00.entity.ChatContent;
import com.project.project00.entity.ChatRoomInfo;
import com.project.project00.entity.ChatRoomMember;
import com.project.project00.entity.ScheduleList;
import com.project.project00.service.ChatContentService;
import com.project.project00.service.ChatRoomListService;
import com.project.project00.service.ChatRoomMemberService;
import com.project.project00.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class ChatRestController {

    @Autowired
    private ChatRoomListService chatRoomListService;

    @Autowired
    private ChatRoomMemberService chatRoomMemberService;

    @Autowired
    private ChatContentService chatContentService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/chatRoom/join")
    public ChatRoomInfo chatRoomJoin(@RequestParam("id") Long id, Model model){


        ChatRoomInfo room = chatRoomListService.findById(id);
        chatRoomListService.chatRoomCountUp(id);

        model.addAttribute("roomId", room.getId());
        model.addAttribute("roomTitle",room.getTitle());
        return room;
    }
    @PostMapping("/chatroom/sendMessage")
    public void SendMessage(@RequestParam("chatContent") String inputChatContent,
                            @RequestParam("userName") String inputUserName,
                            @RequestParam("userId") Long inputUserId,
                            @RequestParam("roomId") Long inputRoomId){
        ChatContent sendMsg = new ChatContent();

        sendMsg.setMessage(inputChatContent);
        sendMsg.setWriterName(inputUserName);
        sendMsg.setRoomId(inputRoomId);
        sendMsg.setWriterId(inputUserId);

        chatContentService.sendMessage(sendMsg);
    }

    @GetMapping("/chatroom/loadInterval")
    public List<ChatContent> intervalLoadMessage(@RequestParam("lastId")Long lastId,
                                                 @RequestParam("roomId")Long roomId,
                                                 @RequestParam("userId")Long userId){
        List<ChatContent> list = chatContentService.intervalLoadMessage(roomId, lastId);
        if ((list != null) && (!list.isEmpty())) {
            chatRoomMemberService.UpdateReadChatId(list.get(list.size() - 1).getId(),roomId,userId);
        }
        return list;
    }

    @GetMapping("/chatroom/roomExit")
    public void exitRoom(@RequestParam("roomId")Long roomId,
                         @RequestParam("userId")Long userId,
                         @RequestParam("userNick")String userNick){
        chatRoomMemberService.exitRoomMember(roomId, userId);
        ChatContent sysMsg = new ChatContent();
        sysMsg.setWriterId(userId);
        sysMsg.setWriterName("시스템 메시지");
        sysMsg.setRoomId(roomId);
        sysMsg.setMessage(userNick+"님이 퇴장하셨습니다.");
        chatContentService.sendMessage(sysMsg);
    }

    @GetMapping("/chatroom/memberSearch")
    public List<String> roomMemberSearch(@RequestParam("roomId")Long roomId){
        List<Long> memberList = chatRoomMemberService.roomMemberSearch(roomId);

        List<String> member = new ArrayList<String>();
        for (int i = 0; i < memberList.size(); i++) {
            Long memberId = memberList.get(i);
            String nickname = userInfoService.findNicknameById(memberId);
            member.add(nickname);
        }

        return member;
    }

    @GetMapping("/myChatListViewById")
    public List<ChatRoomInfo> myChatListView(@RequestParam("id")Long userId){
        List<ChatRoomMember> roomlist = chatRoomMemberService.myRoomSearchById(userId);

        List<ChatRoomInfo> list = new ArrayList<>();
        if(roomlist != null){
            for(ChatRoomMember i : roomlist) {
                ChatRoomInfo room = chatRoomListService.findById(i.getRoomId());
                int count = chatContentService.notReadMessageCheck(i.getRoomId(), i.getReadChatId());
                room.setCount(count);
                list.add(room);
            }
        }
        Comparator<ChatRoomInfo> roomComparator = Comparator.comparing(ChatRoomInfo::getCount);
        Collections.sort(list, roomComparator);

        return list;
    }

    @GetMapping("/chatPasswordCheck")
    public String boardPasswordCheck(@RequestParam("roomId")Long roomId,
                                     @RequestParam("userId")Long userId){
        if(chatRoomMemberService.secretChatMemberCheck(roomId, userId) > 0)return "-1";
        else return chatRoomListService.passwordCheck(roomId);
    }
    @GetMapping("/chatRoomDelete")
    public void chatRoomDelete(@RequestParam("roomId")Long roomId){
        chatRoomListService.deleteRoom(roomId);
    }

}

