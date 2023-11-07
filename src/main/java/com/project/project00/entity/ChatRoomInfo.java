package com.project.project00.entity;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "chat_room_info")
public class ChatRoomInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 채팅방의 id값 @Id 는 기본키임을 지정하는 JPA 어노테이션 GeneratedValue는 Mysql(마리아db)에게 마지막 데이터로부터 1씩 증가하는 시퀀스같은 컬럼인 것을 알려주는 어노테이션

    // 채팅방 제목
    private String title;

    // 채팅방 비밀번호 secret 속성이 True일 경우에만 작성
    @ColumnDefault("0")
    private String password;

    // 채팅방 생성자의 id값 회원정보의 index값을 의미 게시판 생성자는 자동으로 관리자 권한을 부여받음
    private Long admin;

    // 위도 지도상 위아래 남북방향 Latitude
    private Double lat;

    // 경도 지도상 좌우 동서방향 longitude
    private Double lng;

    // 검색 가능 여부 확인 1이 가능
    private int search;

    // 비밀채팅방의 경우 값 1 비밀번호 입력 값2 지도에서 비공개 값3 지도에서 비공개 + 비밀번호 입력 공개방은 값0
    private int secret;

    //채팅방 생성 날짜
    @CreationTimestamp
    private Date regDate;

    // 방문자 수
    @ColumnDefault("0")
    private int count;
}
