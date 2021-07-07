package com.kh.sample01.service;

import com.kh.sample01.vo.MemberVo;

public interface MemberService {
	public MemberVo login(String user_id, String user_pw);
	public boolean checkDupId(String user_id);
	public void insertMember(MemberVo memberVo);
	public int getUserPoint(String user_id);
}
