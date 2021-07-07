package com.kh.sample01.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sample01.dao.MemberDao;
import com.kh.sample01.dao.MessageDao;
import com.kh.sample01.dao.PointDao;
import com.kh.sample01.vo.MessageVo;
import com.kh.sample01.vo.PointVo;

@Service
public class MessageServiceImpl implements MessageService{

	@Inject
	private MessageDao messageDao;
	
	@Inject
	private PointDao pointDao;
	
	@Inject
	private MemberDao memberDao;
	
	@Transactional
	@Override
	public void sendMessage(MessageVo messageVo) {
		// 이거 전체가 하나의 트렌잭션이 됨 ( 다 성공하거나, 다 롤백하거나 )
		messageDao.insertMessage(messageVo);
		PointVo pointVo = new PointVo(messageVo.getMsg_sender(), PointDao.SEND_MESSAGE_CODE, PointDao.SEND_MESSAGE_POINT);
		pointDao.insertPoint(pointVo);
		memberDao.updatePoint(messageVo.getMsg_sender(), PointDao.SEND_MESSAGE_POINT);
	}

	@Override
	public int notReadCount(String msg_receiver) {
		int count = messageDao.notReadCount(msg_receiver);
		return count;
	}

	@Override
	public List<MessageVo> messageListNotRead(String msg_receiver) {
		List<MessageVo> list = messageDao.messageListNotRead(msg_receiver);
		return list;
	}

	@Override
	public List<MessageVo> messageListReceive(String msg_receiver) {
		List<MessageVo> list = messageDao.messageListReceive(msg_receiver);
		return list;
	}

	@Override
	@Transactional
	public MessageVo messageRead(int msg_no, String user_id) {
		MessageVo messageVo = messageDao.readMessage(msg_no);
		// 읽지 않은 쪽지에만 포인트 추가해줘야함
		// 읽은 날을 구해서 읽은 날이 없는 경우에만 포인트 추가(읽지 않은 메세지를 읽은 경우에만 할 작업)
		// 날짜가 update되기 전에 날짜를 가져오기 때문에(vo안에 날짜) if안에서 날짜가 null값임 --> getOpenDate쿼리문 추가해서 해결
		Timestamp msg_opendate = messageVo.getMsg_opendate();
		if (msg_opendate == null) {
			// 메세지 읽은날자 오늘로 업데이트(?) vo가 위로 올라가서 여긴 null값.. db에는 들어감
			messageDao.updateOpenDate(msg_no);
			// 회원의 포인트를 +해줌
			memberDao.updatePoint(user_id, PointDao.READ_MESSAGE_POINT);
			PointVo pointVo = new PointVo(user_id, PointDao.READ_MESSAGE_CODE, PointDao.READ_MESSAGE_POINT);
			// 포인트 테이블에 회원 포인트 추가
			pointDao.insertPoint(pointVo);
			// opendate값을 dao에 쿼리를 가져와서 설정해줌
			msg_opendate = messageDao.getOpenDate(msg_no);
			messageVo.setMsg_opendate(msg_opendate);
		}
		return messageVo;
	}

	@Override
	public boolean deleteMessage(int msg_no, String user_id) {
		return messageDao.deleteMessage(msg_no, user_id);
	}

}
