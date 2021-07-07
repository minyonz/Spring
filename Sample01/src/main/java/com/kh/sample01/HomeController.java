package com.kh.sample01;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.sample01.service.MemberService;
import com.kh.sample01.service.MessageService;
import com.kh.sample01.util.MyFileUploadUtil;
import com.kh.sample01.vo.MemberVo;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private MemberService memberService;
	
	@Inject
	private MessageService messageService;

	// value -> 요청경로, GET방식으로 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/board/listAll";
	}
	
	// 응답하는 데이터에 대한 한글처리
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces="application/text;charset=utf-8")
	@ResponseBody
	public String uploadAjax(MultipartFile file) throws Exception {
		System.out.println("file:" + file);
		String originalFilename = file.getOriginalFilename();
		System.out.println("originalFilename:" + originalFilename);
		String filePath = MyFileUploadUtil.uploadFile("D:/upload", originalFilename, file.getBytes());
		return filePath;
	}
	
	// 썸네일 이미지 요청
	@RequestMapping(value = "/displayImage", method = RequestMethod.GET)
	@ResponseBody
	public byte[] displayImage(String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		// 바이트배열로 바꿔줌
		byte[] bytes = IOUtils.toByteArray(fis);
		// 스트림 안닫아주니까 삭제속도 느렸다
		fis.close();
		return bytes;
	}
	
	// 첨부파일 삭제
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFile(String fileName) throws Exception {
		MyFileUploadUtil.deleteFile(fileName);
		return "success";
	}
	
	// 로그인 폼
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() throws Exception {
		return "loginForm";
	}
	
	// 로그인 실행
	@RequestMapping(value = "/loginRun", method = RequestMethod.POST)
	public String loginRun(String user_id, String user_pw, RedirectAttributes rttr, HttpSession session) throws Exception {
		MemberVo memberVo = memberService.login(user_id, user_pw);
		String msg = null;
		String page = null;
		// 성공하면 리스트, 실패하면 다시 로그인 폼 
		if (memberVo != null) {
			// 읽지 않은 쪽지(msg_receiver -> user_id)
			int notReadCount = messageService.notReadCount(user_id);
			memberVo.setNotReadCount(notReadCount);
			msg = "success";
			page = "redirect:/board/listAll";
			// 세션에 로그인한 정보를 담아줌
			session.setAttribute("loginVo", memberVo);
		} else {
			msg = "fail";
			page = "redirect:/loginForm";
		}
		rttr.addFlashAttribute("msg", msg);
		return page;
	}
	
	// 회원 가입 폼
	@RequestMapping(value = "/memberJoinForm", method = RequestMethod.GET)
	public String memberJoinForm() throws Exception {
		return "memberJoinForm";
	}
	
	// 아이디 중복 확인
	@RequestMapping(value = "/checkDupId", method = RequestMethod.GET)
	@ResponseBody
	public String checkDupId(String user_id) throws Exception {
		boolean result = memberService.checkDupId(user_id);
		return String.valueOf(result);
	}
	
	// 회원가입 실행
	@RequestMapping(value = "/memberJoinRun", method = RequestMethod.POST)
	public String memberJoinRun(MemberVo memberVo, MultipartFile file, RedirectAttributes rttr) throws Exception {
		String orgFileName = file.getOriginalFilename();
		System.out.println("orgFileName:" + orgFileName);
		String filePath = MyFileUploadUtil.uploadFile("D:/user_pic", orgFileName, file.getBytes());
		memberVo.setUser_pic(filePath);
		memberService.insertMember(memberVo);
		rttr.addFlashAttribute("msg", "success");
		System.out.println("memberVo:" + memberVo);
		return "redirect:/loginForm";
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {
		session.invalidate(); // 현재 세션 무효화 , removeAttribute() -> 모두
		return "redirect:/loginForm";
	}
}













