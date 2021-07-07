package com.kh.sample01.vo;

import java.sql.Timestamp;

public class MemberVo {
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_email;
	private Timestamp reg_date;
	private Timestamp update_date;
	// 포인트 추가
	private int user_point;
	// 사진컬럼 추가
	private String user_pic;
	// 안읽은 쪽지카운트 추가
	private int notReadCount;


	public MemberVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberVo(String user_id, String user_pw, String user_name, String user_email, Timestamp reg_date,
			Timestamp update_date, int user_point, String user_pic) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.user_email = user_email;
		this.reg_date = reg_date;
		this.update_date = update_date;
		this.user_point = user_point;
		this.user_pic = user_pic;
	}

	public int getNotReadCount() {
		return notReadCount;
	}
	
	public void setNotReadCount(int notReadCount) {
		this.notReadCount = notReadCount;
	}
	public int getUser_point() {
		return user_point;
	}
	
	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}
	public String getUser_pic() {
		return user_pic;
	}

	public void setUser_pic(String user_pic) {
		this.user_pic = user_pic;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Timestamp getReg_date() {
		return reg_date;
	}

	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	@Override
	public String toString() {
		return "MemberVo [user_id=" + user_id + ", user_pw=" + user_pw + ", user_name=" + user_name + ", user_email="
				+ user_email + ", reg_date=" + reg_date + ", update_date=" + update_date + ", user_point=" + user_point
				+ ", user_pic=" + user_pic + ", notReadCount=" + notReadCount + "]";
	}

}
