package com.kh.sample01.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.vo.PointVo;

@Repository
public class PointDaoImpl implements PointDao {

	private static String NAMESPACE = "com.kh.sample01.point.";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public void insertPoint(PointVo pointVo) {
		sqlSession.insert(NAMESPACE + "insertPoint", pointVo);
	}


}
