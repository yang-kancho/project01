package com.project.project00.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "chat_room_member")
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name="join_time")
    @CreationTimestamp
    private Timestamp joinTime;

    @Column(name = "start_chat_id")
    private Long startChatId;

    @Column(name = "read_chat_id")
    private Long readChatId;
}
