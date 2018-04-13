package com.winterframework.firefrog.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.notice.entity.NoticeMqMsg;
import com.winterframework.firefrog.user.entity.NoticeMsg;
import com.winterframework.firefrog.user.entity.UserInboxMessage;
import com.winterframework.firefrog.user.vo.ImGroupMessageVo;
import com.winterframework.firefrog.user.vo.ImGroupUserVo;
import com.winterframework.firefrog.user.vo.ImGroupVo;
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
public interface IMessage2Service {

	public Page<UserInboxMessage> getMessageList(PageRequest<Long> pr);

	public Page<UserInboxMessage> getUnReadMessageList(PageRequest<Long> pr);

	public int getUnreadMessagesNumber(long userId);

	public List<UserInboxMessage> getMessageDetail(long groupId, long userId);
	
	public List<UserInboxMessage> getMessageDetail(long groupId);
	
	public void deleteMessage(Long userId, Long groupId);

	public void deleteMessages(Long userId,MessageMarkAndDeleteRequestDTO[] groupIds);

	public Boolean checkUserTo(String account,Long userId,MessageRecipient[] receivers);

	public void markRead(Long userId, MessageMarkAndDeleteRequestDTO[] groupIds);

	public ImGroupVo queryOrCreateImGroup(Long... userIds) throws Exception;

	public ImGroupUserVo queryOrAddImGroupUser(ImGroupVo group, Long userId);

	public ImGroupMessageVo saveImGroupMessage(ImGroupMessageVo msgVo);

	public void addGroupOtherUserUnReadCount(ImGroupUserVo user, Long count);

	public void sendMessage(Long userId, String account, Long receiveId, String content)
			throws Exception;

	public void replyMessage(Long groupId, Long userId, String account, String content) throws Exception;

	public Page<UserInboxMessage> queryMessageList(
			PageRequest<MessageQueryRequest> pageRequest);

	public void sendMessageIntoMq(String queueName, NoticeMqMsg noticeMqMsg) throws Exception;

	public Page<NoticeMsg> queryUnreadNoticePushMsgList(
			PageRequest<Long> pr) throws Exception;

	public void markNoticeRead(Long userId, MessageMarkAndDeleteRequestDTO[] ids);
	
}
