package com.project.project00.repository;

import com.project.project00.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {
    List<BoardComment> findByBoardContentIdOrderByIdDesc(Long id);


    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM board_comment WHERE board_content_id = :contentId", nativeQuery = true)
    @Transactional
    void deleteByContentId(@Param("contentId")Long contentId);
}