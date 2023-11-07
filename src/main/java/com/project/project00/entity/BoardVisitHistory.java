package com.project.project00.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Data
@Entity(name = "board_visit_history")
public class BoardVisitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long boardId;

    private Double lat;

    private Double lng;

    private String boardTitle;

    @CreationTimestamp
    private Timestamp visitTime;
}
