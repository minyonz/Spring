package com.kh.sample01.dao;

import java.sql.Timestamp;
import java.util.List;

import com.kh.sample01.vo.MessageVo;

public interface MessageDao {
	public void insertMessage(MessageVo messageVo);
	public int notReadCount(String msg_receiver);
	public List<MessageVo> messageListNotRead(String msg_receiver);
	public List<MessageVo> messageListReceive(String msg_receiver);
	// 메세지 읽었을 때 
	public void updateOpenDate(int msg_no);
	public MessageVo readMessage(int msg_no);
	// 메세지 오픈된 날짜 구하기
	public Timestamp getOpenDate(int msg_no);
	// 메세지 삭제
	public boolean deleteMessage(int msg_no, String user_id);
}
