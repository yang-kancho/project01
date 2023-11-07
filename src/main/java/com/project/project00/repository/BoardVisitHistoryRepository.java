package com.project.project00.repository;

import com.project.project00.entity.BoardVisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardVisitHistoryRepository extends JpaRepository<BoardVisitHistory,Long> {



    @Query(value = "SELECT b.* FROM board_visit_history b WHERE b.user_id = :userId AND b.id = (SELECT MAX(id) FROM board_visit_history WHERE user_id = :userId AND board_id = b.board_id) ORDER BY visit_time DESC", nativeQuery = true)
    @Transactional
    List<BoardVisitHistory> findByUserId(@Param("userId")Long userId);


    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM board_visit_history WHERE board_id = :boardId", nativeQuery = true)
    @Transactional
    void deleteByBoardId(@Param("boardId")Long boardId);

    @Query(value = "SELECT count(*) FROM board_visit_history WHERE board_id = :boardId AND user_id = :userId", nativeQuery = true)
    @Transactional
    int secretHistoryCheck(@Param("boardId")Long boardId, @Param("userId")Long userId);
}
