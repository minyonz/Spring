package com.kh.sample01.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.sample01.service.CommentService;
import com.kh.sample01.vo.CommentVo;
import com.kh.sample01.vo.MemberVo;

// 모든 메서드에 @ResponseBody가 붙어있는 것 
// 비동기 요청에 대한 전용 컨트롤러!
//( return 값을 그냥 그 자체를 보냄 (.jsp이런걸 보내는게 아니라)
// 응답 데이터가 String 타입인 경우에도 jsp로 포워드 하는 것이 아니고, String 데이터를 응답한다.

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Inject
	private CommentService commentService;
	
	// 댓글 목록
	// content.jsp에서 넘어온 b_no값 받음 
	@RequestMapping(value = "/getCommentList/{b_no}", method=RequestMethod.GET)
	// 경로변수 Path~(b_no)를 지역변수 int b_no에 저장한다
	public List<CommentVo> getCommentList(@PathVariable("b_no") int b_no) throws Exception {
//		List<CommentVo> list = new ArrayList<CommentVo>();
//		for (int i = 1; i <= 10; i++) {
//			CommentVo vo = new CommentVo(i, b_no, "hong", "댓글" + i, null);
//			list.add(vo);
//		}
		List<CommentVo> list = commentService.getCommentList(b_no);
		return list;
	}
	
	// 댓글 쓰기 - c_content, b_no, user_id
	// 비동기 방식 - RequestBody붙여줘야함
	@RequestMapping(value = "/insertComment", method=RequestMethod.POST)
	public String insertComment(@RequestBody CommentVo commentVo, HttpSession session) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		commentVo.setUser_id(memberVo.getUser_id());
		System.out.println(commentVo);
		commentService.insertComment(commentVo);
		return "success";
	}
	
	@RequestMapping(value = "/updateComment", method=RequestMethod.POST)
	public String updateComment(@RequestBody CommentVo commentVo) throws Exception {
//		System.out.println(commentVo);
		commentService.updateComment(commentVo);
		return "success";
	}
	
	@RequestMapping(value = "/deleteComment/{c_no}/{b_no}", method=RequestMethod.GET)
	public String deleteComment(@PathVariable("c_no") int c_no, @PathVariable("b_no") int b_no) throws Exception {
		System.out.println("c_no:" + c_no);
		System.out.println("b_no:" + b_no);
		commentService.deleteComment(c_no, b_no);
		return "success";
	}
}



















