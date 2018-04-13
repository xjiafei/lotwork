package com.winterframework.firefrog.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.web.dto.MessageMarkAndDeleteRequestDTO;
import com.winterframework.firefrog.user.web.dto.MessageQueryRequest;
import com.winterframework.firefrog.user.web.dto.MessageRecipient;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 站内信服务接口
 * @author tod
 * 
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface IMessageService {

	public Page<MessageTopic> getMessageList(PageRequest<Long> pr);
	
	public Page<MessageTopic> queryMessageList(PageRequest<MessageQueryRequest> pageRequest);

	public Page<MessageTopic> getUnReadMessageList(PageRequest<Long> pr);

	public int getUnreadMessagesNumber(long userId);

	public void markRead(MessageMarkAndDeleteRequestDTO[] topicIds);

	public void deleteMessages(MessageMarkAndDeleteRequestDTO[] topicIds);

	public void batchUpdateMessage(List<MessageTopic> messageTopicList);

	public List<Message> getMessageDetail(long topicId, long userId);
	
	public List<Message> getMessageDetail(long topicId);

	public void sendMessages(long[] receiverIds, long senderId, String senderName, String title, String content)
			throws Exception;

	public void sendMessages(long receiverId, long senderId, String senderName, String title, String content)
			throws Exception;

	public void replyMessage(long replier, long topicId, long parentId, String content) throws Exception;
	
	public void deleteMessage(Long noticeId);

	public void sendMessages(long[] receiverIds, String title, String content, Long effectHours,Long noticeMsgId) throws Exception;

	public Boolean checkUserTo(String account,Long userId,MessageRecipient[] receivers);
		
}
