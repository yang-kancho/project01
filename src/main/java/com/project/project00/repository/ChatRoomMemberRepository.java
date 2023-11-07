package com.project.project00.repository;


import com.project.project00.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember,Long> {



    @Query(value = "SELECT start_chat_id FROM chat_room_member r WHERE r.user_id = :userId and r.room_id = :roomId", nativeQuery = true)
    @Transactional
    Long findMemberJoin(@Param("userId") Long userId,@Param("roomId") Long roomId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE chat_room_member r SET r.start_chat_id = :startId , r.read_chat_id = :startId WHERE r.id = :id", nativeQuery = true)
    @Transactional
    int updateStartId(@Param("startId")Long startId,@Param("id")Long id);


    @Query(value = "SELECT r.start_chat_id FROM chat_room_member r WHERE r.room_id = :roomId and r.user_id = :userId", nativeQuery = true)
    @Transactional
    Long startIdCheck(@Param("roomId") Long roomId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM chat_room_member WHERE room_id = :roomId AND user_id = :userId", nativeQuery = true)
    @Transactional
    void exitRoomMember(@Param("roomId")Long roomId,@Param("userId")Long userId);


    @Query(value = "SELECT user_id FROM chat_room_member WHERE room_id = :roomId", nativeQuery = true)
    @Transactional
    List<Long> roomMemberSearch(@Param("roomId")Long roomId);

    @Query(value = "SELECT * FROM chat_room_member WHERE user_id = :userId", nativeQuery = true)
    @Transactional
    List<ChatRoomMember> myRoomSearchById(@Param("userId")Long userId);

    @Query(value = "SELECT count(*) FROM chat_room_member WHERE room_id = :roomId AND user_id = :userId", nativeQuery = true)
    @Transactional
    int secretChatMemberCheck(@Param("roomId")Long roomId, @Param("userId")Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE chat_room_member SET read_chat_id = :chatId WHERE id=:id", nativeQuery = true)
    @Transactional
    void UpdateReadChatId(@Param("chatId")Long chatId,@Param("id")Long id);

    @Query(value = "SELECT id FROM chat_room_member WHERE room_id = :roomId AND user_id = :userId", nativeQuery = true)
    @Transactional
    Long searchId(@Param("roomId") Long roomId,@Param("userId") Long userId);
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM chat_room_member WHERE room_id=:roomId", nativeQuery = true)
    @Transactional
    void deleteByRoomId(@Param("roomId")Long roomId);
}
