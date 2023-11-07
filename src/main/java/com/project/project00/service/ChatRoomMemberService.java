package com.project.project00.service;

import com.project.project00.entity.ChatRoomMember;
import com.project.project00.repository.ChatRoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomMemberService {

    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;


    public Long intoUser(ChatRoomMember member){
        Long userId = member.getUserId();
        Long roomId = member.getRoomId();

        Long check = chatRoomMemberRepository.findMemberJoin(userId, roomId);

        if(check == null) {// 만약 멤버에 없다면 새로 추가 있으면 동작없음
            Long id = chatRoomMemberRepository.save(member).getId();
            return id;
        }else{
            return 0L;
        }
    }
    public int updateStartId(Long startId, Long id){
        return chatRoomMemberRepository.updateStartId(startId, id);
    }

    public Long findToStartId(Long roomId, Long userId){
        return chatRoomMemberRepository.startIdCheck(roomId, userId);
    }

    public void exitRoomMember(Long roomId, Long userId){
        chatRoomMemberRepository.exitRoomMember(roomId, userId);
    }

    public List<Long> roomMemberSearch(Long roomId) {
        List<Long> memberList = chatRoomMemberRepository.roomMemberSearch(roomId);
        if (memberList != null) {
            return memberList;
        } else {
            return new ArrayList<Long>(); // 빈 리스트 반환
        }
    }

    public List<ChatRoomMember> myRoomSearchById(Long userId){
        return chatRoomMemberRepository.myRoomSearchById(userId);
    }

    public int secretChatMemberCheck(Long roomId, Long userId){
        return chatRoomMemberRepository.secretChatMemberCheck(roomId, userId);
    }

    public void UpdateReadChatId(Long chatId, Long roomId, Long userId){
        Long id = chatRoomMemberRepository.searchId(roomId, userId);
        chatRoomMemberRepository.UpdateReadChatId(chatId,id);
    }


}
