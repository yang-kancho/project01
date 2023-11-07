package com.project.project00.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "board_content")
public class BoardContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //게시물 기본 키값(인덱스)

    // 게시글이 들어있는 게시판의 ID
    private Long boardId;

    //게시글 작성자의 닉네임
    private String writer;

    //게시글 작성자의 ID(인덱스)
    private Long writerid;

    //게시글 제목
    private String title;

    //게시글의 내용
    private String content;

    //게시글 작성시간
    @CreationTimestamp
    private Date regdate;

    //게시글 조회수
    private Long count;
}
