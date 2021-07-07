package com.kh.sample01.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.vo.BoardVo;
import com.kh.sample01.vo.PagingDto;

// () <- 이름을 지어 주는것
@Repository("dao1")
public class BoardDaoImlp implements BoardDao {

	private static final String NAMESPACE = "com.kh.sample01.board.";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int getNextVal() {
		int nextval = sqlSession.selectOne(NAMESPACE + "getNextVal");
		return nextval;
	}

	@Override
	public void insertArticle(BoardVo boardVo) {
		sqlSession.insert(NAMESPACE + "insertArticle", boardVo);
	}
	
	@Override
	public void insertAttach(BoardVo boardVo) {
		// 파일이름 얻어냄
		String[] files = boardVo.getFiles();
		if (files != null && files.length > 0) {
			for (String file : files) {
				Map<String, Object> map = new HashMap<>();
				map.put("file_name", file);
				map.put("b_no", boardVo.getB_no());
				System.out.println("file:" + file);
				System.out.println(boardVo.getB_no());
				// 파일 갯수만큼 insert함
				sqlSession.insert(NAMESPACE + "insertAttach", map);			
			}			
		}
	}
	@Override
	public BoardVo selectByBno(int b_no) {
		BoardVo boardVo = sqlSession.selectOne(NAMESPACE + "selectByBno", b_no);
		return boardVo;
	}

	@Override
	public void updateArticle(BoardVo boardVo) {
		sqlSession.update(NAMESPACE + "updateArticle", boardVo);

	}

	@Override
	public void deleteArticle(int b_no) {
		sqlSession.delete(NAMESPACE + "deleteArticle", b_no);

	}

	@Override
	public List<BoardVo> listAll(PagingDto pagingDto) {
		List<BoardVo> list = sqlSession.selectList(NAMESPACE + "listAll", pagingDto);
		return list;
	}

	@Override
	public int getCount(PagingDto pagingDto) {
		int count = sqlSession.selectOne(NAMESPACE + "getCount", pagingDto);
		return count;
	}

	@Override
	public void updateViewCnt(int b_no) {
		sqlSession.update(NAMESPACE + "updateViewCnt", b_no);
	}

	@Override
	public void updateCommentCnt(int b_no, int count) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("b_no", b_no);
		map.put("count", count);
		sqlSession.update(NAMESPACE + "updateCommentCnt", map);
	}

	@Override
	public void updateLikeCnt(int b_no, int count) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("b_no", b_no);
		map.put("count", count);
		sqlSession.update(NAMESPACE + "updateLikeCnt", map);
	}

	@Override
	public void insertLike(int b_no, String user_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("b_no", b_no);
		map.put("user_id", user_id);
		sqlSession.insert(NAMESPACE + "insertLike", map);
	}

	@Override
	public void deleteLike(int b_no, String user_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("b_no", b_no);
		map.put("user_id", user_id);
		sqlSession.insert(NAMESPACE + "deleteLike", map);
	}

	@Override
	public int likeCheck(int b_no, String user_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("b_no", b_no);
		map.put("user_id", user_id);
		System.out.println("dao / b_no:" + b_no + "user_id:" + user_id);
		int count = sqlSession.selectOne(NAMESPACE + "likeCheck", map);
		return count;
	}

	@Override
	public int likeAll(int b_no) {
		int likeAllCount = sqlSession.selectOne(NAMESPACE + "likeAll", b_no);
		return likeAllCount;
	}

}
