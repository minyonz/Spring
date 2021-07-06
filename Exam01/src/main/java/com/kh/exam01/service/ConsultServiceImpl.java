package com.kh.exam01.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kh.exam01.domain.ConsultVo;
import com.kh.exam01.persitence.ConsultDao;

@Service
public class ConsultServiceImpl implements ConsultService {

	@Inject
	ConsultDao consultDao;
	
	@Override
	public List<ConsultVo> listConsult(String sno) {
		List<ConsultVo> list = consultDao.listConsult(sno);
		return list;
	}

}
