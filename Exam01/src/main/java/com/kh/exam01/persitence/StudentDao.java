package com.kh.exam01.persitence;

import java.util.List;

import com.kh.exam01.domain.StudentVo;

public interface StudentDao {
	public void insertStudent(StudentVo studentVo);
	public void updateStudent(StudentVo studentVo);
	public void deleteStduent(String sno);
	public List<StudentVo> listStudent();
	public StudentVo selectStudent(String sno);
	public int checkSno(String sno);
}
