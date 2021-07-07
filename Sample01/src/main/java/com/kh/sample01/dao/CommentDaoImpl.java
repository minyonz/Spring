package com.kh.sample01.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.vo.CommentVo;

@Repository
public class CommentDaoImpl implements CommentDao {

	private static final String NAMESPACE = "com.kh.sample01.comment.";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<CommentVo> getCommentList(int b_no) {
		List<CommentVo> list = sqlSession.selectList(NAMESPACE + "getCommentList", b_no);
		return list;
	}

	@Override
	public void insertComment(CommentVo commentVo) {
		sqlSession.insert(NAMESPACE + "insertComment", commentVo);
	}

	@Override
	public void updateComment(CommentVo commentVo) {
		sqlSession.update(NAMESPACE + "updateComment", commentVo);
	}

	@Override
	public void deleteComment(int c_no) {
		sqlSession.delete(NAMESPACE + "deleteComment", c_no);
	}

}
