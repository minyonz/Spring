package com.kh.sample01.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.vo.MemberVo;
import com.kh.sample01.vo.MessageVo;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	// member-mapper의 getTime을 쓰기 위해 상수값 정의
	private static final String NAMESPACE = "com.kh.sample01.member.";
	
	// 스프링에 등록되어있음, 커넥션 풀에서 얻어와서 가져옴
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public Timestamp getTime() {
		// member-mapper.xml id=""
		Timestamp time = sqlSession.selectOne(NAMESPACE + "getTime");
		System.out.println("time:" + time);
		return time;
	}

	@Override
	public void insertMember(MemberVo memberVo) {
		// 아이디값에 지정된(insertMember) 실행
		// 파라미터로 memberVo전달
		sqlSession.insert(NAMESPACE + "insertMember", memberVo);
	}

	@Override
	public MemberVo selectMember(String user_id) {
		MemberVo memberVo = sqlSession.selectOne(NAMESPACE + "selectMember", user_id);
		return memberVo;
	}

	@Override
	public MemberVo login(String user_id, String user_pw) {
		// 값 전달할때 하나밖에 전달 못함
		// MemberVo를 넘겨줘도 되지만 만약 전달할 값이 많을 경우에 Map을 사용해서 담아서 보냄
		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("user_pw", user_pw);
		MemberVo memberVo = sqlSession.selectOne(NAMESPACE + "login", map); 
		return memberVo;
	}

	@Override
	public void updateMember(MemberVo memberVo) {
		sqlSession.update(NAMESPACE + "updateMember", memberVo); 
	}

	@Override
	public void deleteMember(String user_id) {
		sqlSession.delete(NAMESPACE + "deleteMember", user_id);
	}

	@Override
	public List<MemberVo> memberList() {
		List<MemberVo> list = sqlSession.selectList(NAMESPACE + "memberList");
		return list;
	}

	@Override
	public void updatePoint(String user_id, int point_score) {
		Map<String, Object> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("point_score", point_score);
		sqlSession.update(NAMESPACE + "updatePoint", map);
		
	}

	@Override
	public boolean checkDupId(String user_id) {
		int count = sqlSession.selectOne(NAMESPACE + "checkDupId", user_id);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int getUserPoint(String user_id) {
		int user_point = sqlSession.selectOne(NAMESPACE + "getUserPoint", user_id);
		return user_point;
	}
	
}
