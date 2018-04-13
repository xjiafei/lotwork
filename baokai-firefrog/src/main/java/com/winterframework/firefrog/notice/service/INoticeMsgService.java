package com.winterframework.firefrog.notice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface INoticeMsgService {

	public Page<NoticeMsg> queryMsg(PageRequest<NoticeMsgSearchDto> search) throws Exception;

	public void modifyMsgStatus(Long id, Long status) throws Exception;
	
	public void saveMsg(NoticeMsg msg,RabbitTemplate rabbitTemplate) throws Exception;
	
	public NoticeMsg queryMsgDetail(Long id) throws Exception;
	
	public void sendMsg(Long sendUserId, String sendAccount, Long receiveId,
			String content) throws Exception;
	
	public void sendMsgIntoMq(NoticeMsg msg) throws Exception;
	
	public void pushScheduleMsgToMq(RabbitTemplate rabbitTemplate) throws Exception;

	public void saveNoticeMsgPush(Long receiveId, Long noticeMsgId) throws Exception;


}
