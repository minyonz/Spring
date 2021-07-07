package com.kh.sample01.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.sample01.vo.MemberVo;

//로그인 되지 않은 사용자가 글을 쓰려고 할때 가로채서 로그인화면으로 이동
public class AuthInterceptor extends HandlerInterceptorAdapter{

	// servlet-context에서 bean으로 등록하고 어떤 요청을 인터셉트 할 건지 등록해줘야함
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 세션에서 loginVo값이 있는지 체크(login이 성공했는지)
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo)session.getAttribute("loginVo");
		// 로그인 되어 있지 않다면
		if (memberVo == null) {
			response.sendRedirect("/loginForm");
			return false; // 요청처리를 중단
		}
		return true; // 요청 처리를 계속함
	}

}
