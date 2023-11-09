# project01
실행에 필요한 테이블 생성 쿼리
CREATE TABLE `user_info_data_entity` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `startlat` double DEFAULT NULL,
  `startlng` double DEFAULT NULL,
  `imagefilename` varchar(1000) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='지역 좌표 정보 저장';

CREATE TABLE `board_list` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `admin` int(30) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `search` int(11) NOT NULL DEFAULT 1,
  `secret` int(11) NOT NULL DEFAULT 0,
  `reg_date` date NOT NULL DEFAULT current_timestamp(),
  `count` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `admin_to_userId_idx` (`admin`),
  CONSTRAINT `admin_to_userId` FOREIGN KEY (`admin`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE `board_content` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `board_id` int(30) NOT NULL,
  `writer` varchar(50) NOT NULL,
  `writerid` int(30) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` mediumtext NOT NULL,
  `regdate` date NOT NULL DEFAULT current_timestamp(1),
  `count` int(30) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `ref_board_id_idx` (`board_id`),
  KEY `ref_user_info_id_idx` (`writerid`),
  CONSTRAINT `ref_board_id` FOREIGN KEY (`board_id`) REFERENCES `board_list` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `ref_user_info_id` FOREIGN KEY (`writerid`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=647 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `board_comment` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `writer` varchar(50) NOT NULL,
  `writer_id` int(30) NOT NULL,
  `board_content_id` int(30) NOT NULL,
  `comment_content` varchar(200) NOT NULL,
  `regdate` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_info_id_idx` (`writer_id`),
  KEY `board_content_id_idx` (`board_content_id`),
  CONSTRAINT `board_content_id` FOREIGN KEY (`board_content_id`) REFERENCES `board_content` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `comment_writer` FOREIGN KEY (`writer_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `board_bookmark` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `member_id` int(30) NOT NULL,
  `board_id` int(30) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `board_title` varchar(50) NOT NULL,
  `regdate` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `board_id_idx` (`board_id`),
  KEY `user_info_id_idx` (`member_id`),
  CONSTRAINT `board_id` FOREIGN KEY (`board_id`) REFERENCES `board_list` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_info_id` FOREIGN KEY (`member_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `board_visit_history` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `user_id` int(30) NOT NULL,
  `board_id` int(30) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `board_title` varchar(50) NOT NULL,
  `visit_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `visit_from_userId_idx` (`user_id`),
  KEY `visit_board_id_idx` (`board_id`),
  CONSTRAINT `visit_board_id` FOREIGN KEY (`board_id`) REFERENCES `board_list` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `visit_from_userId` FOREIGN KEY (`user_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chat_room_info` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `admin` int(30) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `search` int(11) NOT NULL DEFAULT 1,
  `secret` int(11) NOT NULL DEFAULT 0,
  `reg_date` date NOT NULL DEFAULT current_timestamp(),
  `count` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `lat` (`lat`,`lng`),
  KEY `admin_user_id_idx` (`admin`),
  CONSTRAINT `admin_user_id` FOREIGN KEY (`admin`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE `chat_content` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `writer_id` int(30) NOT NULL,
  `writer_name` varchar(50) NOT NULL,
  `room_id` int(30) NOT NULL,
  `message` varchar(255) NOT NULL,
  `regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `content_in_room_idx` (`room_id`),
  KEY `chatcontent_user_id_idx` (`writer_id`),
  CONSTRAINT `chatcontent_user_id` FOREIGN KEY (`writer_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `content_in_room` FOREIGN KEY (`room_id`) REFERENCES `chat_room_info` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chat_room_member` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `user_id` int(30) NOT NULL,
  `room_id` int(30) NOT NULL,
  `join_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `start_chat_id` int(30) DEFAULT 0,
  `read_chat_id` int(30) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `members_room_id_idx` (`room_id`),
  KEY `chatmember_user_id_idx` (`user_id`),
  CONSTRAINT `chatmember_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `members_room_id` FOREIGN KEY (`room_id`) REFERENCES `chat_room_info` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `direct_message` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `from_id` int(30) NOT NULL,
  `to_id` int(30) NOT NULL,
  `from_nickname` varchar(50) NOT NULL,
  `to_nickname` varchar(50) NOT NULL,
  `message` varchar(255) NOT NULL,
  `send_time` datetime NOT NULL DEFAULT current_timestamp(),
  `reading` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `message_user_id_idx` (`from_id`,`to_id`),
  CONSTRAINT `message_from_user` FOREIGN KEY (`from_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE `friend_list` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `from_id` int(30) NOT NULL,
  `to_id` int(30) NOT NULL,
  `from_nickname` varchar(50) NOT NULL,
  `agree` int(11) NOT NULL DEFAULT 0,
  `send_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `friend_user_idx` (`from_id`),
  CONSTRAINT `friend_user` FOREIGN KEY (`from_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE `schedule_list` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `admin` int(30) NOT NULL,
  `schedule_title` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `datetime` datetime(6) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `reg_date` datetime(6) NOT NULL,
  `secret` int(11) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `schedule_admin_id_idx` (`admin`),
  CONSTRAINT `schedule_admin_id` FOREIGN KEY (`admin`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `schedule_member_list` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `user_id` int(30) NOT NULL,
  `schedule_id` int(30) NOT NULL,
  `admin_id` int(30) NOT NULL,
  `join_time` datetime NOT NULL DEFAULT current_timestamp(),
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `schedule_title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `schedule_member_user_idx` (`user_id`),
  CONSTRAINT `schedule_member_user` FOREIGN KEY (`user_id`) REFERENCES `user_info_data_entity` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


CREATE TABLE `schedule_participants` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(30) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcw8cwpc90n7vo6y7cc3vbmx3k` (`schedule_id`),
  CONSTRAINT `FKcw8cwpc90n7vo6y7cc3vbmx3k` FOREIGN KEY (`schedule_id`) REFERENCES `schedule_list` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

