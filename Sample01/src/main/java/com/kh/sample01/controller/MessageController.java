package com.kh.sample01.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.sample01.service.MemberService;
import com.kh.sample01.service.MessageService;
import com.kh.sample01.vo.MemberVo;
import com.kh.sample01.vo.MessageVo;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Inject
	private MessageService messageService;
	
	@Inject
	private MemberService memberService;
	
	// 쪽지 보내기
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public String sendMessage(@RequestBody MessageVo messageVo, HttpSession session) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		messageVo.setMsg_sender(memberVo.getUser_id());
		System.out.println("MessageVo:" + messageVo);
		messageService.sendMessage(messageVo);
		return "success";
	}
	
	// 읽지 않은 메세지 갯수(HomeController, 로그인실행에 정의)
	
	// 읽지 않은 메세지 목록
	@RequestMapping(value = "/messageListNotRead", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageVo> messageListNotRead(HttpSession session) throws Exception {
		// 세션의 loginVo를 받아옴
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		List<MessageVo> list = messageService.messageListNotRead(memberVo.getUser_id());
		return list;
	}
	
	// 받은 메세지 목록
	@RequestMapping(value = "/message_receive_list", method = RequestMethod.GET)
	public String messageListReceive(HttpSession session, Model model) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		String msg_receiver = memberVo.getUser_id();
		List<MessageVo> list = messageService.messageListReceive(msg_receiver);
//		System.out.println("message list:" + list);
		model.addAttribute("list", list);
		return "message/message_receive_list";
	}
	
	// 쪽지 상세보기(session-> 로그인한 사용자)
	@RequestMapping(value = "/messageRead", method = RequestMethod.GET)
	public String messageRead(int msg_no, HttpSession session, Model model) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		String user_id = memberVo.getUser_id();
		MessageVo messageVo = messageService.messageRead(msg_no, user_id);
		model.addAttribute("messageVo", messageVo);
		// 읽지않은 쪽지 카운트, 포인트도 구해서 값 설정
		int notReadCount = messageService.notReadCount(user_id);
		int user_point = memberService.getUserPoint(user_id);
		memberVo.setNotReadCount(notReadCount);
		memberVo.setUser_point(user_point);
//		memberVo.setNotReadCount(memberVo.getNotReadCount() - 1);
		return "message/message_read";
	}
	
	// 쪽지 삭제
	// 쪽지번호만 알면 삭제하는 것이 아니라 로그인한 사용자값도 같이 받아와서 삭제함
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.GET)
	public String deleteMessage(int msg_no, HttpSession session, RedirectAttributes rttr) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		String user_id = memberVo.getUser_id();
		boolean result = messageService.deleteMessage(msg_no, user_id);
		rttr.addFlashAttribute("msg_delete", String.valueOf(result));
		return "redirect:/message/message_receive_list";
	}
	
	// 쪽지 답장
	@RequestMapping(value = "/replyMessage", method = RequestMethod.POST)
	@ResponseBody
	public String replyMessage(MessageVo messageVo, HttpSession session) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		messageVo.setMsg_sender(memberVo.getUser_id());
		messageService.sendMessage(messageVo);
		return "success";
	}
}
















