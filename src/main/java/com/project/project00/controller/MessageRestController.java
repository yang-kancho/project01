package com.project.project00.controller;


import com.project.project00.entity.FriendList;
import com.project.project00.entity.UserInfoDataEntity;
import com.project.project00.service.DirectMessageService;
import com.project.project00.service.FriendListService;
import com.project.project00.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageRestController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    FriendListService friendListService;

    @Autowired
    DirectMessageService directMessageService;



    @GetMapping("/searchById")
    public List<UserInfoDataEntity> searchById(@RequestParam("userId")String userId){

        List<UserInfoDataEntity> list = userInfoService.searchByUserId(userId);

        return list;
    }

    @GetMapping("/searchByNick")
    public List<UserInfoDataEntity> searchByNick(@RequestParam("userNick")String userNick){
        List<UserInfoDataEntity> list = userInfoService.searchByUserNick(userNick);

        return list;
    }

    @GetMapping("/friendRequest")
    public int friendRequest(@RequestParam("id")Long id,
                                @SessionAttribute("userId")Long userId){
        FriendList friend = new FriendList();
        friend.setFromId((userId));
        friend.setToId(id);
        if(userId.equals(id)){
            return -2;
        }

        return friendListService.friendRequest(friend);
    }
    @GetMapping("/friendRequestCheck")
    public List<FriendList> friendRequestCheck(@RequestParam("userId")Long userId){
        List<FriendList> list = new ArrayList<>();
        list = friendListService.findMyRequestCheck(userId);


        return list;

    }
    @GetMapping("/friendRequestAnswer")
    public int friendRequestAnswer(@RequestParam("id")Long id,
                                   @RequestParam("type")int answer){

        return friendListService.RequestAnswer(id,answer);
        // 0 거절 1 수락
    }

    @GetMapping("/myFriendListView")
    public List<UserInfoDataEntity> myFriendListView(@RequestParam("id")Long userId){
        List<UserInfoDataEntity> list = new ArrayList<>();
        List<FriendList> result = friendListService.myFriendList(userId);
        if(!result.isEmpty()){
            for(int i = 0; i < result.size(); i++){
                if(result.get(i).getFromId() == userId){
                    list.add(userInfoService.searchUserInfo(result.get(i).getToId()));
                }else{
                    list.add(userInfoService.searchUserInfo(result.get(i).getFromId()));
                }
            }
        }
        return list;
    }

    @GetMapping("/friendDelete")
    public void friendDelete(@RequestParam("id")Long id, @RequestParam("userId")Long userId){
        friendListService.deleteFriend(id,userId);
    }

}
