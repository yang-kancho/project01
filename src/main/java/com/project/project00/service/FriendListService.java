package com.project.project00.service;

import com.project.project00.entity.FriendList;
import com.project.project00.repository.FriendListRepository;
import com.project.project00.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendListService {
    @Autowired
    FriendListRepository friendListRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public int friendRequest(FriendList friend){
        Optional<Long> result = friendListRepository.friendFind(friend.getFromId(),friend.getToId());
        if(!result.isPresent()){
            String fromNickname = userInfoRepository.findNicknameById(friend.getFromId());
            friend.setFromNickname(fromNickname);
            friendListRepository.save(friend);
            return -1;
            //친구요청을 보냄
        }else if(result.get() == 0){
            // 친구요청을 보내거나 받았지만 응답받지 못함.
            return 0;
        }else{
            // 이미 친구.
            return 1;
        }
    }

    public List<FriendList> findMyRequestCheck(Long userId){
        return friendListRepository.findMyRequestCheck(userId);
    }

    public int RequestAnswer(Long id, int answer){
        if(answer == 0){
            //거절
            friendListRepository.deleteById(id);
            return answer;
        }else{
            friendListRepository.updateToAgree(id);
            return answer;

            //수락
        }

    }
    public void deleteFriend(Long id,Long userId){
        friendListRepository.deleteFriend(id, userId);
    }

    public List<FriendList> myFriendList(Long userId){
        return friendListRepository.myFriendList(userId);
    }
}
