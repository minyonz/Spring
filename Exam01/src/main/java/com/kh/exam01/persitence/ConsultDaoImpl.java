package com.kh.exam01.persitence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.exam01.domain.ConsultVo;

@Repository
public class ConsultDaoImpl implements ConsultDao {

	private static final String NAMESPACE = "com.kh.exam01.consult.";
	
	@Inject
	SqlSession sqlSession;

	@Override
	public List<ConsultVo> listConsult(String sno) {
		List<ConsultVo> list = sqlSession.selectList(NAMESPACE + "listConsult", sno);
		return list;
	}

}
