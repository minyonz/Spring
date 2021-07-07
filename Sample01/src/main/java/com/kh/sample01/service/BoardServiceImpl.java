package com.kh.sample01.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sample01.dao.BoardDao;
import com.kh.sample01.vo.BoardVo;
import com.kh.sample01.vo.PagingDto;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	@Qualifier("dao1")
	private BoardDao boardDao;
	
	@Override
	public List<BoardVo> listAll(PagingDto pagingDto) {
		List<BoardVo> list = boardDao.listAll(pagingDto);
		return list;
	}

	@Transactional
	@Override
	public void writeRun(BoardVo boardVo) {
		int nextval = boardDao.getNextVal();
		System.out.println(nextval);
		boardVo.setB_no(nextval);
		boardDao.insertArticle(boardVo);
		boardDao.insertAttach(boardVo);
	}

	@Override
	public BoardVo content(int b_no) {
		BoardVo boardVo = boardDao.selectByBno(b_no);
		boardDao.updateViewCnt(b_no);
		return boardVo;
	}

	@Override
	public void modifyRun(BoardVo boardVo) {
		boardDao.updateArticle(boardVo);
	}

	@Override
	public void deleteRun(int b_no) {
		boardDao.deleteArticle(b_no);
	}

	// pagingDto의 count
	@Override
	public int getCount(PagingDto pagingDto) {
		int count = boardDao.getCount(pagingDto);
		return count;
	}

	@Transactional
	@Override
	public boolean like(int b_no, String user_id) {
		// 좋아요 클릭 여부 확인
		int count = boardDao.likeCheck(b_no, user_id);
		// 클릭 했다면 
		if (count > 0) {
			boardDao.deleteLike(b_no, user_id);
			boardDao.updateLikeCnt(b_no, -1);
			return true;
		} else {
			boardDao.insertLike(b_no, user_id);
			boardDao.updateLikeCnt(b_no, 1);
			return false;
		}
	}

	@Override
	public int likeAll(int b_no) {
		int likeAllCount = boardDao.likeAll(b_no);
		return likeAllCount;
	}

	@Override
	public int likeCheck(int b_no, String user_id) {
		int count = boardDao.likeCheck(b_no, user_id);
		return count;
	}

}
