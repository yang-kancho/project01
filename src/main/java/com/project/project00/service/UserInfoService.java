package com.project.project00.service;


import com.project.project00.entity.UserInfoDataEntity;
import com.project.project00.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {

	@Autowired
	UserInfoRepository userInfoRepository;

	// 회원 가입 기능
	public String saveUserInfo(UserInfoDataEntity userInfoDataEntity) {
		// DB에 저장
		String userId = userInfoDataEntity.getUserid(); // 가입한 유저아이디가 있는지 찾을값
		String userEmail = userInfoDataEntity.getEmail(); // 가입한 유저이메일이 있는지 찾을값
		// 1.기존에 가입했는지 확인
		Optional<UserInfoDataEntity> byUserid = userInfoRepository.findByUseridAndEmail(userId, userEmail);
		// "UserInfoDataEnity" 테이블에서 조회해온 데이터를 byUserid에 저장
		String result = null;
		if (byUserid.isPresent() || userId.isEmpty() || userEmail.isEmpty()) {
			// 조회시 값이있을경우 ( 회원가입 폼 돌아갈 준비)


			result = "false";

		} else {

			userInfoRepository.save(userInfoDataEntity);

			result = "success"; // 가입 성공
		}

		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 아이디 체크
	public boolean userIdChk(String userid) {
		Optional<UserInfoDataEntity> serch = userInfoRepository.findByUserid(userid);
		boolean result = true;
		if (serch.isPresent()) {

			result = true;
		} else if (!serch.isPresent()) {

			result = false;
		}

		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 이메일 체크
	public boolean emailChk(String email) {

		Optional<UserInfoDataEntity> emailSerch = userInfoRepository.findByEmail(email);
		boolean result = true;
		if (emailSerch.isPresent()) {
			result = true;
		} // 존재할떄
		else if (!emailSerch.isPresent()) {
			result = false;
		} // 존재하지않을때

		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 닉네임 체크
	public boolean nicknameChk(String nickname) {
		Optional<UserInfoDataEntity> nicknameSerch = userInfoRepository.findByNickname(nickname);
		boolean result = true;
		if (nicknameSerch.isPresent()) {
			result = true;
		} // 존재할떄
		else if (!nicknameSerch.isPresent()) {
			result = false;
		} // 존재하지않을때

		return result;
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//로그인 기능
	public UserInfoDataEntity login(UserInfoDataEntity userInfoDataEntity) {

		// 1. userId로 DB 조회
		String userid = userInfoDataEntity.getUserid();
		String userpasword = userInfoDataEntity.getPassword();
		Optional<UserInfoDataEntity> byUserid = userInfoRepository.findByUseridAndPassword(userid, userpasword);

		if (byUserid.isPresent()) {
			// 조회했을때 값이있을경우(회원이 있을경우)
			UserInfoDataEntity getUserInfoData = byUserid.get();
			if (getUserInfoData.getPassword().equals(userInfoDataEntity.getPassword())) {
				// 비밀번호 일치시

				return getUserInfoData;
			} else {
				// 비밀번호가 틀릴경우

				return null;
			}
		}
//		// 조회 결과가 없을떄
		return null;
	}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//아이디 찾기
	public String searchId(String email) {

		Optional<UserInfoDataEntity> getUserId = userInfoRepository.findByEmail(email);
		if(!getUserId.isPresent()){
			return "null";
		}else{
			String userid = getUserId.get().getUserid();
			return userid;
		}

	}

	// 비밀번호 찾기(변경)
	@Transactional
	public String updatePwd(String userid, String email, String password) {
		UserInfoDataEntity getUserIdAndEmail = userInfoRepository.findByUseridAndEmail(userid,email).get();
		if(getUserIdAndEmail.getEmail() == null || getUserIdAndEmail.getUserid() == null){
			return "0";
		}else{
			userInfoRepository.save(getUserIdAndEmail);
			return "1";
		}

	}
	public UserInfoDataEntity getUserInfo(Long id){return userInfoRepository.findById(id).get();}


	public void updateInfo(Long id, String userid, String nickname, String email, String password, String imagefilename){
		UserInfoDataEntity updateInfo = userInfoRepository.findById(id).get();
		updateInfo.setUserInfo(id, userid, nickname, email, password, imagefilename);
		System.out.println(updateInfo);
		userInfoRepository.save(updateInfo);
	}

	public String findNicknameById(Long userId){
		return userInfoRepository.findNicknameById(userId);
	}

	public List<UserInfoDataEntity> searchByUserId(String userId){
		return userInfoRepository.searchByUserId(userId);
	}

	public List<UserInfoDataEntity> searchByUserNick(String userNick){return userInfoRepository.searchByUserNick(userNick);}
	public UserInfoDataEntity searchUserInfo(Long id){return userInfoRepository.findById(id).get();}

}
