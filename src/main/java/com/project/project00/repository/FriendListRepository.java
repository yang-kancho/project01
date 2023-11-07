package com.project.project00.repository;

import com.project.project00.entity.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList,Long>{

    @Query("SELECT agree FROM friend_list WHERE from_id = :fromId AND to_id = :toId OR from_id = :toId AND to_id = :fromId")
    @Transactional
    Optional<Long> friendFind(@Param("fromId") Long fromId,@Param("toId") Long toId);

    @Query(value = "SELECT * FROM friend_list WHERE to_id = :userId AND agree = 0",nativeQuery = true)
    @Transactional
    List<FriendList> findMyRequestCheck(@Param("userId")Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE friend_list SET agree = 1 WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateToAgree(@Param("id")Long id);

    @Query(value = "SELECT * FROM friend_list WHERE to_id = :userId AND agree = 1 OR from_id = :userId AND agree = 1;",nativeQuery = true)
    @Transactional
    List<FriendList> myFriendList(@Param("userId")Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM friend_list WHERE from_id = :id AND to_id = :userId OR from_id = :userId AND to_id = :id", nativeQuery = true)
    @Transactional
    void deleteFriend(@Param("id")Long id, @Param("userId")Long userId);
}
