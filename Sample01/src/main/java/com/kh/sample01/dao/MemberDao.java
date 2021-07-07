package com.kh.sample01.dao;

import java.sql.Timestamp;
import java.util.List;

import com.kh.sample01.vo.MemberVo;
import com.kh.sample01.vo.MessageVo;

public interface MemberDao {
	public Timestamp getTime();
	public void insertMember(MemberVo memberVo);
	public MemberVo selectMember(String user_id);
	public MemberVo login(String user_id, String user_pw);
	public void updateMember(MemberVo memberVo);
	public void deleteMember(String user_id);
	public List<MemberVo> memberList();
	public void updatePoint(String user_id, int point_score);
	public boolean checkDupId(String user_id);
	// 포인트 가져오기
	public int getUserPoint(String user_id);
}
