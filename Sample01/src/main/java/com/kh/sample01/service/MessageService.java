package com.kh.sample01.service;

import java.util.List;

import com.kh.sample01.vo.MessageVo;

public interface MessageService {
	public void sendMessage(MessageVo messageVo);
	public int notReadCount(String msg_receiver);
	public List<MessageVo> messageListNotRead(String msg_receiver);
	public List<MessageVo> messageListReceive(String msg_receiver);
	// 메세지 읽었을 때 ( dao는 updateOpenDate )
	public MessageVo messageRead(int msg_no, String user_id);
	public boolean deleteMessage(int msg_no, String user_id);
}
