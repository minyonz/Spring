package com.kh.sample01.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 로그인 되지 않은 사용자가 글을 쓰려고 할때 가로채서 로그인화면으로 이동
// 세션만료로 로그인 정보가 날아(?) 갔을 때 글쓰기 등을 막기 위해 사용함
public class SampleInterceptor extends HandlerInterceptorAdapter {

	// (가로 채서) 해당 요청이 실행되기 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle...");
//		return super.preHandle(request, response, handler); // 이건 실행 함
		return false; // 요청에 대한 실행 중지 -> doA실행 안함
	}
	
	
	// 해당 요청이 실행 되고 난 후
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle...");
		super.postHandle(request, response, handler, modelAndView);
	}
}
