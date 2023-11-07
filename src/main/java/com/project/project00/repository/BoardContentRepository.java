package com.project.project00.repository;

import com.project.project00.entity.BoardContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardContentRepository extends JpaRepository<BoardContent,Long> {
    Page<BoardContent> findByBoardId(Long boardId, Pageable pageable);




    @Query(value = "SELECT id FROM board_content WHERE board_id = :boardId", nativeQuery = true)
    @Transactional
    List<Long> selectContentIdByBoardId(@Param("boardId")Long boardId);

}
