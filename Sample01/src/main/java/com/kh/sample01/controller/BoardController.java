package com.kh.sample01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.sample01.service.BoardService;
import com.kh.sample01.vo.BoardVo;
import com.kh.sample01.vo.MemberVo;
import com.kh.sample01.vo.PagingDto;

@Controller
// 모든경로에 공통 경로를 주고싶을 경우 클래스 위에 작성하면 됨
@RequestMapping(value="/board")
public class BoardController {
	
	@Inject
	private BoardService boardService;
	
	// (PagingDto pagingDto = new PagingDto로 자동으로 객체 생성된 상태, 기본생성자)
	// localhost/board/listAll 
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public String listAll(Model model, PagingDto pagingDto) throws Exception {
		int count = boardService.getCount(pagingDto);
		// 페이지 관리를 setCount로 , 기존model2에서 생성자 안에 만들어 놓은 수식들을 setCount쪽으로 옮김
		pagingDto.setCount(count);
		System.out.println("pagingDto:" + pagingDto);
		List<BoardVo> list = boardService.listAll(pagingDto);
		// 반환타입이 String이니까 Model로 추가해줌
		model.addAttribute("list", list);
		model.addAttribute("pagingDto", pagingDto);
		return "board/listAll"; // /WEB-INF/views/board/listAll.jsp
	}
	
	@RequestMapping(value="/writeForm", method=RequestMethod.GET)
	public String writeForm() throws Exception {
		return "board/writeForm";
	}
	
	@RequestMapping(value="/writeRun", method=RequestMethod.POST)
	// 원래는 String user_id = request.getParameter("user_id")로
	// 각 값을 다 구해서 new boardVo(user_id,  .... )이런식으로 넣어줬는데
	// spring에서는 바로 BoardVo를 넣어주면 알아서 값을 찾아서 넣어줌
	// 대신 html태그의 이름(name)과 BoardVo의 필드의 setter값이 같아야함 name="user_id" , vo.setUser_id(user_id)
	// - 로그인 값을 얻어내기 위해 세션 사용
	public String writeRun(BoardVo boardVo, RedirectAttributes rttr, HttpSession session) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		boardVo.setUser_id(memberVo.getUser_id());
		System.out.println("BoardController, writeRun, boardVo:" + boardVo);
		boardService.writeRun(boardVo);
		// session대신 사용하는것
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/listAll";
	}

	// @Model~~ 저 어노테이션 생략해도 값 잘 전달됨
	@RequestMapping(value="/content", method=RequestMethod.GET)
	public String content(/*@ModelAttribute("pagingDto")*/ PagingDto pagingDto, int b_no, Model model, HttpSession session) throws Exception {
		BoardVo boardVo = boardService.content(b_no);
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		String user_id = memberVo.getUser_id();
		// 상세보기 페이지 들어갈 때 하트 클릭 여부 확인
		int checkCount = boardService.likeCheck(b_no, user_id);
		model.addAttribute("checkCount", String.valueOf(checkCount));
		model.addAttribute("boardVo", boardVo);
		return "board/content";
	}
	
	// 수정
	@RequestMapping(value="/modifyRun", method=RequestMethod.POST)
	public String modifyRun(BoardVo boardVo, RedirectAttributes rttr) throws Exception {
		System.out.println("BoardController, modifyRun, boardVo:" + boardVo);
		boardService.modifyRun(boardVo);
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/content?b_no=" + boardVo.getB_no();
	}
	
	// 삭제
	@RequestMapping(value="/deleteRun", method=RequestMethod.GET)
	public String deleteRun(int b_no, RedirectAttributes rttr) throws Exception {
		boardService.deleteRun(b_no);
		rttr.addFlashAttribute("msgDelete", "success");
		return "redirect:/board/listAll";
	}
	
	// 좋아요 
	@RequestMapping(value = "/like/{b_no}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> like(@PathVariable("b_no") int b_no, HttpSession session) throws Exception {
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		String user_id = memberVo.getUser_id();
		System.out.println("b_no:" + b_no + "user_id:" + user_id);
		boolean result = boardService.like(b_no, user_id);
		// 좋아요 전체 수 가져오기
		int likeAllCount = boardService.likeAll(b_no);
		Map<String, Object> map = new HashMap<>();
		// map에 좋아요 전체갯수, 좋아요클릭 or 취소 여부 담아서 리턴
		map.put("likeCount", likeAllCount);
		if (result == true) {
			map.put("cancel", "cancel");
			return map;
		} 
		map.put("like", "like");
		return map;
	} 
	
	
}
