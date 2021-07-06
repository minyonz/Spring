package com.kh.exam01.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.exam01.domain.ConsultVo;
import com.kh.exam01.service.ConsultService;

// 비동기 요청 컨트롤러
@RestController
@RequestMapping(value="/consult")
public class ConsultController {
	
	@Inject
	ConsultService consultService;
	
	@RequestMapping(value = "/listConsult", method = RequestMethod.GET)
	public List<ConsultVo> listConsult(String sno) throws Exception {
		List<ConsultVo> list = consultService.listConsult(sno); 
		return list;
	}
}
