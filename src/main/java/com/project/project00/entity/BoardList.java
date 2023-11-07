package com.project.project00.entity;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "board_list")
@Data
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 게시판의 id값 @Id 는 기본키임을 지정하는 JPA 어노테이션 GeneratedValue는 Mysql(마리아db)에게 마지막 데이터로부터 1씩 증가하는 시퀀스같은 컬럼인 것을 알려주는 어노테이션

    // 게시판 제목
    private String title;

    // 게시판 비밀번호 secret 속성이 True일 경우에만 작성
    @ColumnDefault("0")
    private String password;

    // 게시판 생성자의 id값 회원정보의 index값을 의미 게시판 생성자는 자동으로 관리자 권한을 부여받음
    private Long admin;

    // 위도 지도상 위아래 남북방향 Latitude
    private Double lat;

    // 경도 지도상 좌우 동서방향 longitude
    private Double lng;

    // 게시판의 카테고리 분류 음식점, 장소, 관광지, 오락시설, 주거지 등등등..
    private String category;

    // 검색 가능 여부 확인 1이 가능
    private int search;

    // 비밀게시판의 경우 값 1 비밀번호 입력 값2 지도에서 비공개 값3 지도에서 비공개 + 비밀번호 입력
    private int secret;

    //게시판 생성 날짜
    @CreationTimestamp
    private Date regDate;

    // 방문자 수
    @ColumnDefault("0")
    private int count;
}
