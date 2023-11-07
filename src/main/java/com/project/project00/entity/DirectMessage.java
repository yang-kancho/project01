package com.project.project00.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "direct_message")
public class DirectMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "to_id")
    private Long toId;

    @Column(name = "from_nickname")
    private String fromNickname;

    @Column(name = "to_nickname")
    private String toNickname;

    @Column(name = "message")
    private String message;

    @Column(name="send_time")
    @CreationTimestamp
    private Timestamp sendTime;

    @Column(name = "reading")
    private int reading;
}
