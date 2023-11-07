package com.project.project00.repository;

import com.project.project00.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList,Long> {

    @Modifying(clearAutomatically = true)
    @Query("update board_list b set b.count = b.count + 1 where b.id = :id")
    @Transactional
    int updateCount(@Param("id") Long id);

    @Query("select title from board_list where id = :id")
    @Transactional
    String findTitleByid(@Param("id") Long id);

    @Query("SELECT password FROM board_list WHERE id = :boardId")
    @Transactional
    String passwordCheck(@Param("boardId")Long boardId);

}

