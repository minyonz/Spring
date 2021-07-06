package com.kh.exam01.persitence;

import java.util.List;

import com.kh.exam01.domain.ConsultVo;

public interface ConsultDao {
	public List<ConsultVo> listConsult(String sno);
}
