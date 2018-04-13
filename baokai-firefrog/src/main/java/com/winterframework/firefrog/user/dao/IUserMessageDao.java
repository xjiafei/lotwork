package com.winterframework.firefrog.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.web.dto.MessageQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * @Description: 站内信的DAO层接口
 * @author Denny
 * @date 2013-4-22 下午2:57:31
 * 
 */
public interface IUserMessageDao {

	/**
	 * 查询站内信列表
	 * 
	 * @param
	 * @return
	 */
	public Page<MessageTopic> getMessageList(PageRequest<Long> pr);
	
	public Page<MessageTopic> selectMessageList(PageRequest<MessageQueryRequest> pageRequest);

	public Page<MessageTopic> getUnReadMessageList(PageRequest<Long> pr);

	/**
	 * 查询站内信主题
	 * 
	 * @param
	 * @return
	 */
	public MessageTopic getMessageTopicByTopicId(long topicId, long userId);

	/**
	 * 查询站内信未读消息总条数
	 * 
	 * @param userId
	 * @return
	 */
	public int getUnreadMessagesNumber(long userId);

	/**
	 * 批量修改站内信消息的状态
	 * 
	 * @param messageTopicList
	 * @return
	 */
	public void batchUpdateMessage(List<MessageTopic> messageTopicList);

	/**
	 * 修改站内信消息的状态
	 * 
	 * @param messageTopicList
	 * @return
	 */
	public void updateMessage(MessageTopic messageTopic);

	/**
	 * 根据id数组获取消息列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<MessageTopic> getMessageListByIds(long[] ids, long userId);

	public void insertMessage(List<MessageTopic> messageTopics);

	public long selectNextId(int count) throws SQLException;
	
	public void deleteMessage(Long noticeId);

	void delete5dayMessage();
	
	/**
	 * 根据receiver修改站内信消息的状态
	 * 
	 * @param userId
	 * @param
	 * @return
	 */
	public void updateMessageByReceiver(Long receiver, MessageTopic messageTopic) throws Exception;
	
	/**
	 * 根据sender修改站内信消息的状态
	 * 
	 * @param receiceAccount
	 * @param
	 * @return
	 */
	public void updateMessageBySender(Long sender, MessageTopic messageTopic) throws Exception;
}
