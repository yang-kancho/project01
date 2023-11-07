package com.project.project00.controller;


import com.project.project00.entity.UserInfoDataEntity;
import com.project.project00.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.lang.System.out;

@Controller
public class UserInfoController {
	@Autowired
	UserInfoService userInfoService;

	//메인 페이지로 이동
	@GetMapping(value={"/","/mainPage"})
	public String moveMainPage(HttpServletRequest request, @SessionAttribute(name = "userId", required = false) Long userId,@SessionAttribute(name = "nickname", required = false) String  nickname, Model model){

		if(userId != null){
			out.println("세션에 저장된 Id : " + userId);
			out.println("세션에 저장된 닉네임 : " + nickname);


			// 여기서 값 전달하기
			model.addAttribute("userId",userId);
			model.addAttribute("nickname",nickname);

			// 로그인 상태면 이동
			return "index";
		}

		return "loginForm";
	}



	// 회원가입 폼으로 이동
	@GetMapping("/insertUserInfoForm")
	public String moveInsert(){

		return "insertUserInfoForm";

	}

	//////////////////////////////////////[ 아이디 중복체크 ]///////////////////////////////////////////////////
	@GetMapping("/userIdChk")
	public String useridChk(String userid){return "idChkpage";}
	@ResponseBody
	@PostMapping("/userIdChk")
	public String getuseridChk(@RequestParam String userid){
		//out.println("테스트 : " + userid);

		String chkResult = "-1";
		boolean result = userInfoService.userIdChk(userid);
		if(result == true) {chkResult = "0";}
		else if(result == false){ chkResult = "1";}
		//out.println(result); // 존재 0, 존재안하면 1
		return chkResult;
	}
	//////////////////////////////////////[ 이메일 중복체크(비동기) ]///////////////////////////////////////////////////
	@ResponseBody
	@PostMapping("/emailChk")
	public String emailChk(@RequestParam String email){
		//out.println("테스트 : " + userid);

		String chkResult = "-1";
		boolean result = userInfoService.emailChk(email);
		if(result == true) {chkResult = "0";}
		else if(result == false){ chkResult = "1";}
		out.println(result); // 존재 0, 존재안하면 1
		out.println(chkResult);

		return chkResult;
	}
	//////////////////////////////////////[ 닉네임 중복체크(비동기) ]///////////////////////////////////////////////////
	@ResponseBody
	@PostMapping("/nicknameChk")
	public String nicknameChk(@RequestParam String nickname){

		String nickChkResult = "-1";
		boolean result = userInfoService.nicknameChk(nickname);
		if(result == true) {nickChkResult = "0";}
		else if(result == false){ nickChkResult = "1";}

		return nickChkResult;
	}


	//////////////////////////////////////[ 회원가입 ]///////////////////////////////////////////////////
	//회원가입 등록
	@PostMapping("/mainPage")
	public String insertUserInfomation(@ModelAttribute UserInfoDataEntity userInfoDataEntity, String imagefilename){
		System.out.println(imagefilename);
		userInfoService.saveUserInfo(userInfoDataEntity);

		return "loginForm";
	}


//////////////////////////////////////[ 로그인 ]///////////////////////////////////////////////////

	@PostMapping("/login")
	public String login(@ModelAttribute UserInfoDataEntity userInfoDataEntity, HttpServletRequest request) {

		UserInfoDataEntity loginData = userInfoService.login(userInfoDataEntity);
		if (loginData != null) {
			// login 성공시
			//세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
			HttpSession session = request.getSession(true);  // Session이 없으면 생성
			// 세션에 userId를 넣어줌
			session.setAttribute("userId", loginData.getId());
			session.setAttribute("nickname", loginData.getNickname());

		}
		return "redirect:/mainPage";
	}

	///////////////////////////////////////[ 로그아웃 ]//////////////////////////////////////////////
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/mainPage";
	}

	///////////////////////////////////////[ 아이디, 비밀번호 찾기 ]//////////////////////////////////////////////
	@GetMapping("/idSearch")
	public String idSearch(){
		return "idSearch";
	}
	@PostMapping("/idSearch")
	@ResponseBody
	public String idSearchDB(@RequestParam String email){

		return userInfoService.searchId(email);
	}



	@GetMapping("/pwSearch")
	public String pwSearch(){return "pwSearch";}

	@PostMapping("/pwdUpdate")
	@ResponseBody
	public String pwSearch(String userid, String email,String password){
		return userInfoService.updatePwd(userid, email,password);
	}


	/// 이미지 저장
	@GetMapping("/imageFile")
	public String imagemove(){
		return "imageFileSave";
	}
	@PostMapping("/imageFile")
	@ResponseBody
	public String image(@RequestParam MultipartFile pic){
		String imageFileName = pic.getOriginalFilename(); // 파일명을 변환
		UUID uuid = UUID.randomUUID();
		String path="C:/project/project00/project00-main/project00-main/project00_test/src/main/resources/static/image/profile/"; // 경로
		String uuidImageFileName = uuid+imageFileName;
		Path imagePath = Paths.get(path + uuidImageFileName); // 저장

		try{
			Files.write(imagePath,pic.getBytes());
		}catch(Exception e){
		}
		return uuidImageFileName;
	}
	// 회원정보 수정페이지로
	@GetMapping("/update")
	public String updatePage(@SessionAttribute(name = "userId", required = false) Long userId,@SessionAttribute(name = "nickname", required = false) String  nickname, Model model){
		if(userId == null){
			return "redirect:/mainPage";
		}
		UserInfoDataEntity userInfo = userInfoService.getUserInfo(userId);
		model.addAttribute("id",userId);
		model.addAttribute("userid",userInfo.getUserid());
		model.addAttribute("nickname",nickname);
		model.addAttribute("email",userInfo.getEmail());
		model.addAttribute("password", userInfo.getPassword());
		model.addAttribute("imagefilename",userInfo.getImagefilename());

		return "updateUserInfo";
	}

	// 회원정보 수정하기
	@PostMapping("/update")
	public String udateInfomation(Long id, String userid,String nickname,String email,String password, String imagefilename){
		if(password == null || password.isEmpty()){
			UserInfoDataEntity userInfo = userInfoService.getUserInfo(id);
			password=userInfo.getPassword();
		}

		userInfoService.updateInfo(id, userid, nickname,email,password, imagefilename);

		return "redirect:/mainPage";
	}

}
