package com.kh.sample01;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.sample01.dao.BoardDao;
import com.kh.sample01.vo.BoardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDaoTest {
	
	// Inject (주입하다), BoardDao타입의 객체를 하나 주입해달라 하는것(new BoardDao() 이런거 안하는것)
	// 싱글톤 만들어주는 것
	@Inject
	// [한정하다], 뭘 쓸것인지 명시해줌, BoardDaoImpl, 2두개라서 에러나는데 명시해주면 에러X
	@Qualifier("dao1")
	private BoardDao boardDao;
	
	@Test
	public void testBoardDao() throws Exception {
		System.out.println(boardDao);
	}
	
	@Test
	public void testInsertArticle() throws Exception {
		BoardVo boardVo = new BoardVo();
		boardVo.setB_title("제목3");
		boardVo.setB_content("내용3");
		boardVo.setUser_id("hong");
		boardDao.insertArticle(boardVo);
	}
	
	@Test
	public void testSelectByBno() throws Exception {
		BoardVo vo = boardDao.selectByBno(1);
		System.out.println(vo);
	}
	
	@Test
	public void testUpdateArticle() throws Exception {
		BoardVo boardVo = new BoardVo();
		boardVo.setB_no(2);
		boardVo.setB_content("내용2");
		boardVo.setB_title("제목2");
		boardDao.updateArticle(boardVo);
	}
	
	@Test
	public void testDeleteArticle() throws Exception {
		boardDao.deleteArticle(5);
	}
	
	@Test
	public void testListAll() throws Exception {
//		List<BoardVo> list = boardDao.listAll();
//		System.out.println(list);
	}

	
}














