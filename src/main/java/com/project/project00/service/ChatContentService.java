package com.project.project00.service;


import com.project.project00.entity.ChatContent;
import com.project.project00.repository.ChatContentRepository;
import com.project.project00.repository.ChatRoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatContentService {

    @Autowired
    private ChatContentRepository chatContentRepository;

    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;

    public void sendMessage(ChatContent content){
        Long id = chatRoomMemberRepository.searchId(content.getRoomId(), content.getWriterId());
        chatRoomMemberRepository.UpdateReadChatId(chatContentRepository.save(content).getId(),id);
    }

    public void sendSystemMessage(ChatContent content, Long id){

        chatRoomMemberRepository.updateStartId(chatContentRepository.save(content).getId(),id);
    }

    public List<ChatContent> firstJoinLoadMessage(Long roomId, Long startId){
        return chatContentRepository.firstJoinLoadMessage(roomId, startId);
    }

    public List<ChatContent> intervalLoadMessage(Long roomId, Long lastId){
        return chatContentRepository.intervalLoadMessage(roomId, lastId);
    }

    public int notReadMessageCheck(Long roomId, Long readId){
        return chatContentRepository.notReadMessageCheck(roomId, readId);
    }


}
