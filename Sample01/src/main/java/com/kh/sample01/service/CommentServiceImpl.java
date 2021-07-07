package com.kh.sample01.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sample01.dao.BoardDao;
import com.kh.sample01.dao.CommentDao;
import com.kh.sample01.vo.CommentVo;

@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentDao commentDao;
	
	@Inject
	private BoardDao boardDao;
	
	@Override
	public List<CommentVo> getCommentList(int b_no) {
		List<CommentVo> list = commentDao.getCommentList(b_no);
		return list;
	}

	@Transactional
	@Override
	public void insertComment(CommentVo commentVo) {
		boardDao.updateCommentCnt(commentVo.getB_no(), 1);
		commentDao.insertComment(commentVo);
	}

	@Override
	public void updateComment(CommentVo commentVo) {
		commentDao.updateComment(commentVo);
	}

	@Transactional
	@Override
	public void deleteComment(int c_no, int b_no) {
		commentDao.deleteComment(c_no);
		boardDao.updateCommentCnt(b_no, -1);
	}

}
