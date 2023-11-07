package com.project.project00.entity;


import lombok.Data;


import javax.persistence.*;
@Data
@Entity
@Table(name = "schedule_participants")

public class ScheduleParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleList schedule;

    @Column
    private String nickname;


}
