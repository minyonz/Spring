package com.kh.exam01.persitence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.exam01.domain.StudentVo;

@Repository
public class StudentDaoImpl implements StudentDao {

	private static final String NAMESPACE = "com.kh.exam01.student.";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public void insertStudent(StudentVo studentVo) {
		sqlSession.insert(NAMESPACE + "insertStudent", studentVo);
	}

	@Override
	public void updateStudent(StudentVo studentVo) {
		sqlSession.update(NAMESPACE + "updateStudent", studentVo);
	}

	@Override
	public void deleteStduent(String sno) {
		sqlSession.delete(NAMESPACE + "deleteStudent", sno);
		
	}

	@Override
	public List<StudentVo> listStudent() {
		List<StudentVo> list = sqlSession.selectList(NAMESPACE + "listStudent");
		return list;
	}

	@Override
	public StudentVo selectStudent(String sno) {
		StudentVo studentVo = sqlSession.selectOne(NAMESPACE + "selectStudent", sno);
		return studentVo;
	}

	@Override
	public int checkSno(String sno) {
		int count = sqlSession.selectOne(NAMESPACE + "checkStudent", sno);
		return count;
	}

}
