package com.project.project00.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity(name = "user_info_data_entity")
@NoArgsConstructor
@ToString
public class UserInfoDataEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
	private Long id;

	private String userid;

	private String email;

	private String nickname;

	@Setter
	private String password;

	@Column(nullable = true)
	private String startlat;

	@Column(nullable = true)
	private String startlng;

	@Column(nullable = true)
	private String imagefilename;

	private String salt;

	public void changePassword(String password) {
		this.password = password;
	}

	public void setUserInfo(Long id,String userid, String nickname, String email,String password, String imageilename){
		this.id = id;
		this.userid =userid;
		this.nickname =nickname;
		this.email=email;
		this.password=password;
		this.imagefilename = imageilename;


	}
	public void setImageFileName(String filename){this.imagefilename = filename;}
}
