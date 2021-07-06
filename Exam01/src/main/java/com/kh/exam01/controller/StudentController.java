package com.kh.exam01.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.exam01.domain.StudentVo;
import com.kh.exam01.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Inject
	StudentService studentService;
	
	// 전체목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listStudent(Model model) throws Exception {
		List<StudentVo> list = studentService.listStudent();
		model.addAttribute("list", list);
		return "student/list";
	}
	
	// 등록폼
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String registForm() throws Exception {
		return "student/regist";
	}
	
	// 등록
	@RequestMapping(value = "/registRun", method = RequestMethod.POST)
	public String registRun(StudentVo studentVo, RedirectAttributes rttr) throws Exception {
		studentService.insertStudent(studentVo);
		rttr.addFlashAttribute("registMsg", "success");
		return "redirect:/student/list";
	}
	
	// 상세보기
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public String content(String sno, Model model) throws Exception {
		StudentVo studentVo = studentService.selectStudent(sno);
		// 성별 체크
		String male = "";
		String female = "";
		if (studentVo.getGender().equals("F")) {
			female = "checked";
		} else if (studentVo.getGender().equals("M")) {
			male = "checked";
		}
		// 학년체크
		String first = "";
		String second = "";
		String third = "";
		String fourth = "";
		if (studentVo.getSyear() == 1) {
			first = "selected";
		} else if (studentVo.getSyear() == 2) {
			second = "selected";
		} else if (studentVo.getSyear() == 3) {
			third = "selected";
		} else if (studentVo.getSyear() == 3) {
			fourth = "selected";
		}
		model.addAttribute("studentVo", studentVo);
		model.addAttribute("male", male);
		model.addAttribute("female", female);
		model.addAttribute("first", first);
		model.addAttribute("second", second);
		model.addAttribute("third", third);
		model.addAttribute("fourth", fourth);
		return "student/content";
	}

	// 수정
	@RequestMapping(value = "/modifyRun", method = RequestMethod.POST)
	public String modifyRun(StudentVo studentVo, RedirectAttributes rttr) throws Exception {
		studentService.updateStudent(studentVo);
		rttr.addFlashAttribute("modifyMsg", "success");
		return "redirect:/student/list";
	}
	
	@RequestMapping(value = "/deleteRun", method = RequestMethod.GET)
	public String deleteRun(String sno, RedirectAttributes rttr) throws Exception {
		System.out.println(sno);
		studentService.deleteStduent(sno);
		rttr.addFlashAttribute("deleteMsg", "success");
		return "redirect:/student/list";
	}
	
	@RequestMapping(value = "/checkSno", method = RequestMethod.GET)
	@ResponseBody
	public int checkSno(String sno) {
		int count = studentService.checkSno(sno);
		return count;
	}
}


















