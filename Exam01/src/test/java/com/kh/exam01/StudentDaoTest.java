package com.kh.exam01;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.exam01.domain.StudentVo;
import com.kh.exam01.persitence.StudentDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class StudentDaoTest {

	@Inject
	StudentDao studentDao;
	
	@Test
	public void testInsertStudent() throws Exception {
//		StudentVo studentVo = new StudentVo("1001", "홍길동", 3, "M", "국문학", 80);
//		StudentVo studentVo = new StudentVo("1002", "강감찬", 3, "M", "영문학", 80);
//		StudentVo studentVo = new StudentVo("1003", "김유신", 2, "M", "국문학", 80);
		StudentVo studentVo = new StudentVo("1004", "장보고", 2, "M", "국문학", 80);
		studentDao.insertStudent(studentVo);
	}
	
	@Test
	public void testUpdateStudent() throws Exception {
		StudentVo studentVo = new StudentVo("1001", "홍길자", 2, "F", "영문학", 30);
		studentDao.updateStudent(studentVo);
	}
	
	@Test
	public void testDeleteStudnet() throws Exception {
		studentDao.deleteStduent("1001");
	}
	
	@Test
	public void testListStudent() throws Exception {
		studentDao.listStudent();
	}
	
	@Test
	public void testSelectStudent() throws Exception {
		studentDao.selectStudent("1002");
	}
	
}













