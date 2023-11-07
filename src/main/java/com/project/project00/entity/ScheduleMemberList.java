package com.project.project00.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity(name = "schedule_member_list")
public class ScheduleMemberList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long scheduleId;

    private Long adminId;

    private Double lat;

    private Double lng;

    private String scheduleTitle;

    @CreationTimestamp
    private Timestamp join_time;
}
