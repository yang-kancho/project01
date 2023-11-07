package com.project.project00.service;

import com.project.project00.entity.ChatContent;
import com.project.project00.entity.ChatRoomInfo;
import com.project.project00.entity.ChatRoomMember;
import com.project.project00.repository.ChatContentRepository;
import com.project.project00.repository.ChatRoomInfoRepository;
import com.project.project00.repository.ChatRoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomListService {

    @Autowired
    private ChatRoomInfoRepository chatRoomInfoRepository;

    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @Autowired
    private ChatContentRepository chatContentRepository;

    @Autowired
    private ChatContentService chatContentService;

    @Autowired
    private ChatRoomMemberService chatRoomMemberService;

    public void createChatRoom(ChatRoomInfo chatRoom){

        Long userId = chatRoom.getAdmin();
        Long roomId = chatRoomInfoRepository.save(chatRoom).getId();
        ChatRoomMember member = new ChatRoomMember();
        member.setRoomId(roomId);
        member.setUserId(userId);
        Long memberIntoId = chatRoomMemberService.intoUser(member);
        Long loadStartId = chatRoomMemberService.findToStartId(roomId, userId);
        if (loadStartId == null) {
            ChatContent sysMsg = new ChatContent();
            sysMsg.setWriterId(userId);
            sysMsg.setWriterName("시스템 메시지");
            sysMsg.setRoomId(roomId);
            sysMsg.setMessage("최초 입장하셨습니다.");
            chatContentService.sendSystemMessage(sysMsg, memberIntoId);
            loadStartId = chatRoomMemberService.findToStartId(roomId, userId);
        }
        List<ChatContent> list = chatContentService.firstJoinLoadMessage(roomId, loadStartId);
        if ((list != null) && (!list.isEmpty())) {
            chatRoomMemberService.UpdateReadChatId(list.get(list.size() - 1).getId(),roomId,userId);
        }
    }
    public List<ChatRoomInfo> chatRoomListAll(){
        return chatRoomInfoRepository.findAll();
    }

    public String findTitleById(Long id){
        return chatRoomInfoRepository.findTitleById(id);
    }

    public int chatRoomCountUp(Long id) {
        return chatRoomInfoRepository.updateCount(id);
    }


    public ChatRoomInfo findById(Long id){
        return chatRoomInfoRepository.findById(id).get();
    }

    public String passwordCheck(Long roomId){
        return chatRoomInfoRepository.passwordCheck(roomId);
    }
    public void deleteRoom(Long roomId){
        chatRoomInfoRepository.deleteRoom(roomId);
        chatRoomMemberRepository.deleteByRoomId(roomId);
        chatContentRepository.deleteByRoomId(roomId);

    }
}
