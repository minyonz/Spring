package com.kh.exam01.domain;

import java.sql.Timestamp;

public class ConsultVo {
	private int c_no;
	private String sno;
	private String c_content;
	private Timestamp c_date;
	
	public ConsultVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsultVo(int c_no, String sno, String c_content, Timestamp c_date) {
		super();
		this.c_no = c_no;
		this.sno = sno;
		this.c_content = c_content;
		this.c_date = c_date;
	}

	public int getC_no() {
		return c_no;
	}

	public void setC_no(int c_no) {
		this.c_no = c_no;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public Timestamp getC_date() {
		return c_date;
	}

	public void setC_date(Timestamp c_date) {
		this.c_date = c_date;
	}

	@Override
	public String toString() {
		return "ConsultVo [c_no=" + c_no + ", sno=" + sno + ", c_content=" + c_content + ", c_date=" + c_date
				+ "]";
	}
	
}
