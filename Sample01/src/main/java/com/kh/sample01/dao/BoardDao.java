package com.kh.sample01.dao;

import java.util.List;

import com.kh.sample01.vo.BoardVo;
import com.kh.sample01.vo.PagingDto;

public interface BoardDao {
	// nextval 얻기
	public int getNextVal();
	// 글쓰기
	public void insertArticle(BoardVo boardVo);
	// 첨부파일 쓰기
	public void insertAttach(BoardVo boardVo);
	// 글조회
	public BoardVo selectByBno(int b_no);
	// 글수정
	public void updateArticle(BoardVo boardVo);
	// 글삭제
	public void deleteArticle(int b_no);
	// 글목록 (페이지, 검색을 위해 PagingDto 값을 넣어 주어야 함)
	public List<BoardVo> listAll(PagingDto pagingDto);
	// 글갯수 (페이지 카운트)
	public int getCount(PagingDto pagingDto);
	// 조회수 증가 (서비스 불필요)
	public void updateViewCnt(int b_no);
	// 댓글 수 변경 (서비스 불필요)
	public void updateCommentCnt(int b_no, int count);
	// 좋아요 수 증가
	public void updateLikeCnt(int b_no, int count);
	// 좋아요 회원
	public void insertLike(int b_no, String user_id);
	// 좋아요 취소 회원
	public void deleteLike(int b_no, String user_id);
	// 좋아요 여부
	public int likeCheck(int b_no, String user_id);
	// 좋아요 전체 갯수
	public int likeAll(int b_no);
}
