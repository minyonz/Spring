package com.kh.sample01;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.sample01.dao.MemberDao;
import com.kh.sample01.vo.MemberVo;

@RunWith(SpringJUnit4ClassRunner.class)
// ** -> 현재폴더랑 하위폴더 포함
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberDaoTest {
	
	// @Autowired(스프링제공) = @Inject(자바제공)
	@Inject
	private MemberDao memberDao;
	
	// 반드시 public void 메서드명() 빈괄호
	@Test
	public void testGetTime() throws Exception{
		memberDao.getTime();
	}
	
	@Test
	public void testInsertMember() throws Exception{
		MemberVo memberVo = new MemberVo("hong", "1234", "홍길동", null, null, null, 0, null);
//		MemberVo memberVo = new MemberVo("kim", "1234", "김길동", null, null, null);
		memberDao.insertMember(memberVo);
	}
	
	@Test
	public void testSelectMember() throws Exception{
		MemberVo memberVo = memberDao.selectMember("hong");
		System.out.println(memberVo);
	}
	
	@Test
	public void testLogin() throws Exception{
		// 비밀번호 틀리게 했을 시 
		// 테스트는 성공, 값은 null이 나옴 
		MemberVo memberVo = memberDao.login("hong", "1235");
		System.out.println(memberVo);
	}
	
	@Test
	public void testUpdateMember() throws Exception{
		MemberVo memberVo = new MemberVo("kim", "4321", "김유신", null, null, null, 0, null);
		memberDao.updateMember(memberVo);
	}
	
	@Test
	public void testDeleteMember() throws Exception{
		memberDao.deleteMember("hong");
	}
	
	@Test
	public void testMemberList() throws Exception{
		List<MemberVo> list = memberDao.memberList();
		System.out.println(list);
	}
}






