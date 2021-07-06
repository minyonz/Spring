package com.kh.exam01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String home(Model model) {
		String name = "김민영";
		String engName = "KimMinYeong";
		
		model.addAttribute("name", name);
		model.addAttribute("engName", engName);
		
		return "home";
	}
	
}
