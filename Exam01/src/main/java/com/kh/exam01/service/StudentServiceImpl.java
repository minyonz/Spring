package com.kh.exam01.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kh.exam01.domain.StudentVo;
import com.kh.exam01.persitence.StudentDao;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Inject
	StudentDao studentDao;
	
	@Override
	public void insertStudent(StudentVo studentVo) {
		studentDao.insertStudent(studentVo);
	}

	@Override
	public void updateStudent(StudentVo studentVo) {
		studentDao.updateStudent(studentVo);
	}

	@Override
	public void deleteStduent(String sno) {
		studentDao.deleteStduent(sno);
	}

	@Override
	public List<StudentVo> listStudent() {
		List<StudentVo> list = studentDao.listStudent();
		return list;
	}

	@Override
	public StudentVo selectStudent(String sno) {
		StudentVo studentVo = studentDao.selectStudent(sno);
		return studentVo;
	}

	@Override
	public int checkSno(String sno) {
		int count = studentDao.checkSno(sno);
		return count;
	}

}
