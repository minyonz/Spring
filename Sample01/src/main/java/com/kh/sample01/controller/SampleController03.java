package com.kh.sample01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController03 {

	@RequestMapping(value = "/doF",  method = RequestMethod.GET)
	public String doF() {
		// 요청은 doF(리다이렉트) -> doG -> do_g.jsp
		System.out.println("doF실행됨");
		return "redirect:/doG";
	}
	
	@RequestMapping(value = "/doG", method = RequestMethod.GET)
	public String doG() {
		System.out.println("doG실행됨");
		return "do_g";
	}
	
	@RequestMapping(value = "/doH", method = RequestMethod.GET)
	public String doH(RedirectAttributes rttr) {
		// 리다이렉트 할 때 저장할 값이 있을 때 사용
		// 요청 doH -> doI로 redirect -> do_i.jsp 실행
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/doI";
	}
	
	@RequestMapping(value = "/doI", method = RequestMethod.GET)
	public String doI() {
		System.out.println("doI실행됨 ..");
		return "do_i";
	}
}












