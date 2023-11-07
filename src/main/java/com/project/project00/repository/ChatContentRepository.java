package com.project.project00.repository;


import com.project.project00.entity.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatContentRepository extends JpaRepository<ChatContent,Long> {



    @Query(value = "SELECT * FROM chat_content c WHERE room_id = :roomId and c.id >= :startId", nativeQuery = true)
    @Transactional
    List<ChatContent>  firstJoinLoadMessage(@Param("roomId")Long roomId, @Param("startId")Long startId);


    @Query(value = "SELECT * FROM chat_content c WHERE room_id = :roomId and c.id > :lastId", nativeQuery = true)
    @Transactional
    List<ChatContent> intervalLoadMessage(@Param("roomId")Long roomId,@Param("lastId") Long lastId);

    @Query(value = "SELECT COUNT(*) FROM chat_content WHERE room_id = :roomId and id > :readId", nativeQuery = true)
    @Transactional
    int notReadMessageCheck(@Param("roomId")Long roomId, @Param("readId")Long readId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM chat_content WHERE room_id = :roomId",nativeQuery = true)
    @Transactional
    void deleteByRoomId(@Param("roomId")Long roomId);

}
