package com.kh.sample01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 어노테이션을 붙여서 컨트롤러를 붙이면 얘는 컨트롤러가 됨
@Controller
public class SampleController01 {
	
	// 이때까지 controller에서 했던 작업을 servlet-context가 해주는것..
	@RequestMapping (value = "/doA", method = RequestMethod.GET)
	public void doA() {
		System.out.println("/doA 실행됨...");
		// 반환 타입이 void인 경우 : 요청명 + .jsp 로 forward 됨. (doA.jsp를 찾음)
		// return "doA" // /WEB-INF/views/doA.jsp
	}
	
	@RequestMapping (value = "/doB", method = RequestMethod.GET)
	public void doB() {
		System.out.println("/doB 실행됨...");
	}
}
