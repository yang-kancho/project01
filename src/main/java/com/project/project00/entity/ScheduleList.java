package com.project.project00.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
@Entity
public class ScheduleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스케줄 생성자의 id값 회원정보의 index값을 의미 게시판 생성자는 자동으로 관리자 권한을 부여받음
    private Long admin;

    private String password;
    //약속시간
    @Column
    private Timestamp datetime;
    //스케줄 제목
    private String scheduleTitle;
    //스케줄 내용
    private String content;

    // 위도 지도상 위아래 남북방향 Latitude
    private Double lat;

    // 경도 지도상 좌우 동서방향 longitude
    private Double lng;

    //비밀방
    private int secret;

    //스케줄 생성 날짜
    @CreationTimestamp
    private Timestamp regDate;


    public void setDatetime(String datetimeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date parsedDate = dateFormat.parse(datetimeString);
            this.datetime = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            // 날짜/시간 형식이 잘못된 경우 예외 처리
            this.datetime = null; // 또는 원하는 기본값으로 설정할 수 있습니다.
            // 예외 처리에 대한 추가적인 로그 작성 또는 오류 메시지 출력 등의 작업을 수행할 수 있습니다.
            System.err.println("Failed to parse datetime: " + datetimeString);
            e.printStackTrace();
        }
    }




}
