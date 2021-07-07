package com.kh.sample01.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.sample01.vo.ProductVo;

@Controller
public class SampleController04 {

	@RequestMapping (value = "/doJSON", method = RequestMethod.GET)
	// 우리가 직접 JSON오브젝트를 만들지 않아도 JSON으로 바꿔줌
	// pom.xml에 Maven~사이트 가서 jackson-databind검색해서 2.5.4 복사해서 넣어줌
	@ResponseBody
	public ProductVo doJSON() {
		ProductVo vo = new ProductVo("냉장고", 300);
		return vo;
	}
	
	// 보통 AJAX요청할때 많이 쓰임
	@RequestMapping (value = "/doJsonList", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductVo> doJsonList()	{
		List<ProductVo> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			ProductVo vo = new ProductVo("냉장고" + i, 300 * i);
			list.add(vo);
		}
		return list;
	}
}
