package com.kh.sample01.service;

import java.util.List;

import com.kh.sample01.vo.BoardVo;
import com.kh.sample01.vo.PagingDto;

public interface BoardService {
	public List<BoardVo> listAll(PagingDto pagingDto);
	public void writeRun(BoardVo boardVo);
	public BoardVo content(int b_no);
	public void modifyRun(BoardVo boardVo);
	public void deleteRun(int b_no);
	// 카운트 구하는 정의(pagingDto)
	public int getCount(PagingDto pagingDto);
	// 좋아요
	public boolean like(int b_no, String user_id);
	// 좋아요 전체 개수
	public int likeAll(int b_no);
	// 좋아요 여부 확인
	public int likeCheck(int b_no, String user_id);
}
