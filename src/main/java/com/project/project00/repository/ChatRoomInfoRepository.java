package com.project.project00.repository;

import com.project.project00.entity.ChatRoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRoomInfoRepository extends JpaRepository<ChatRoomInfo,Long> {



    @Modifying(clearAutomatically = true)
    @Query("SELECT c.title FROM chat_room_info c WHERE c.id = :id")
    @Transactional
    String findTitleById(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE chat_room_info c SET c.count = c.count + 1 WHERE c.id = :id")
    @Transactional
    int updateCount(@Param("id") Long id);

    @Query(value = "SELECT password FROM chat_room_info WHERE id = :roomId", nativeQuery = true)
    @Transactional
    String passwordCheck(@Param("roomId")Long roomId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM chat_room_info WHERE id = :roomId",nativeQuery = true)
    @Transactional
    void deleteRoom(@Param("roomId")Long roomId);
}
