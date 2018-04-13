package com.winterframework.firefrog.user.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.orm.ibatis3.SqlSessionCallback;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.schedule.dto.MessageQueryRequest;
import com.winterframework.firefrog.user.dao.IUserMessageDao;
import com.winterframework.firefrog.user.dao.vo.UserMessage;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * @Description: UserMessage的DAO层
 * @author Denny
 * @date 2013-4-22 下午3:49:36
 * 
 */
@Repository
public class UserMessageDaoImpl extends BaseIbatis3Dao<UserMessage> implements IUserMessageDao {

	@Override
	public int getUnreadMessagesNumber(long userId) {
		Long userID = Long.valueOf(userId);
		Integer count = sqlSessionTemplate.selectOne("getUnreadMessagesNumber", userID);
		return count;
	}

	
	@Override
	public void delete5dayMessage() {

		sqlSessionTemplate.delete("delete5dayMessage");
	}
	
	@Override
	public void batchUpdateMessage(List<MessageTopic> messageTopicList) {
		List<UserMessage> UserMessageList = new ArrayList<UserMessage>();
		if (messageTopicList.size() > 0) {
			for (MessageTopic mt : messageTopicList) {
				UserMessage um = VOConverter.messageTopic2UserMessage(mt);
				UserMessageList.add(um);
			}
		}

		sqlSessionTemplate.selectList("batchUpdate", UserMessageList);

	}

	@Override
	public List<MessageTopic> getMessageListByIds(long[] ids, long userId) {
		List<MessageTopic> messageTopicList = new ArrayList<MessageTopic>();
		List<Long> param = new ArrayList<Long>();
		for (Long l : ids) {
			param.add(l);
		}

		List<UserMessage> userMessageList = this.getByIds(param);
		if (userMessageList.size() > 0) {
			for (UserMessage um : userMessageList) {
				MessageTopic mt = VOConverter.userMessage2MessageTopic(um, userId);
				messageTopicList.add(mt);
			}
		}
		return messageTopicList;
	}

	@Override
	public Page<MessageTopic> getMessageList(PageRequest<Long> pageRequest) {

		Map<String, Object> map = new HashMap<String, Object>();

		long userId = pageRequest.getSearchDo();
		int pageNo = pageRequest.getPageNo();
		int pageSize = pageRequest.getPageSize();

		map.put("userId", userId);
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getMessageListNumber", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<MessageTopic>(0);
		}

		Page<MessageTopic> page = new Page<MessageTopic>(pageNo, pageSize, totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		List<UserMessage> userMessageList = sqlSessionTemplate.selectList("getMessageList", filters, rowBounds);

		List<MessageTopic> messageTopicList = new ArrayList<MessageTopic>();
		if (userMessageList.size() > 0) {
			for (UserMessage um : userMessageList) {
				MessageTopic mt = VOConverter.userMessage2MessageTopic(um, userId);
				messageTopicList.add(mt);
			}
		}
		page.setResult(messageTopicList);
		return page;
	}

	@Override
	public Page<MessageTopic> getUnReadMessageList(PageRequest<Long> pageRequest) {

		Map<String, Object> map = new HashMap<String, Object>();

		long userId = pageRequest.getSearchDo();
		int pageNo = pageRequest.getPageNo();
		int pageSize = pageRequest.getPageSize();

		map.put("userId", userId);
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getMessageListNumber", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<MessageTopic>(0);
		}

		Page<MessageTopic> page = new Page<MessageTopic>(pageNo, pageSize, totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		List<UserMessage> userMessageList = sqlSessionTemplate.selectList("getUnReadMessageList", filters, rowBounds);

		List<MessageTopic> messageTopicList = new ArrayList<MessageTopic>();
		if (userMessageList.size() > 0) {
			for (UserMessage um : userMessageList) {
				MessageTopic mt = VOConverter.userMessage2MessageTopic(um, userId);
				messageTopicList.add(mt);
			}
		}
		page.setResult(messageTopicList);
		return page;
	}

	@Override
	public void insertMessage(List<MessageTopic> messageTopics) {
		final List<UserMessage> messageTopicList = new ArrayList<UserMessage>();
		for (MessageTopic topic : messageTopics) {
			messageTopicList.add(VOConverter.messageTopic2UserMessage(topic));
		}
		//System.out.println("执行批处理insert");
		sqlSessionTemplate.execute(new SqlSessionCallback<Integer>() {
			@Override
			public Integer doInSqlSession(SqlSession sqlSession) throws Exception {
				for (UserMessage entity : messageTopicList) {
					prepareObjectForSaveOrUpdate(entity, true);
					sqlSession.insert(getQueryPath("insertWithNotID"), entity);
				}
				return messageTopicList.size();
			}
		}, ExecutorType.BATCH);
		//System.out.println("执行批处理insert结束");

	}

	@Override
	public long selectNextId(int count) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		long nextId = 0;
		try {
			conn = sqlSessionTemplate.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute("ALTER SEQUENCE SEQ_USER_MESSAGE_ID INCREMENT BY " + count);
			nextId = (Long) sqlSessionTemplate.selectList("selectNextId").get(0);
			stmt.execute("ALTER SEQUENCE SEQ_USER_MESSAGE_ID INCREMENT BY 1");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return nextId;
	}

	@Override
	public MessageTopic getMessageTopicByTopicId(long topicId, long userId) {
		UserMessage um = this.getById(topicId);
		MessageTopic mt = VOConverter.userMessage2MessageTopic(um, userId);
		return mt;
	}

	@Override
	public void updateMessage(MessageTopic messageTopic) {
		UserMessage um = VOConverter.messageTopic2UserMessage(messageTopic);
		this.update(um);
	}

	@Override
	public Page<MessageTopic> selectMessageList(PageRequest<MessageQueryRequest> pageRequest) {
		Page<UserMessage> volist = this.pageQuery(pageRequest, null, null);
		Page<MessageTopic> page = new Page<MessageTopic>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				volist.getTotalCount());
		List<MessageTopic> msgList = new ArrayList<MessageTopic>();
		for (UserMessage vo : volist.getResult()) {
			msgList.add(VOConverter.userMessage2MessageTopic(vo, 0L));
		}
		page.setResult(msgList);
		return page;
	}

	@Override
	public void deleteMessage(Long noticeId) {
		this.sqlSessionTemplate.delete("deleteByNoticeId", noticeId);
	}

	@Override
	public void updateMessageByReceiver(Long receiver, MessageTopic messageTopic)
			throws Exception {
		UserMessage userMessage = new UserMessage();
		userMessage.setReceiver(receiver);
		userMessage.setSendTime(messageTopic.getSendTime());
		userMessage.setReceiveFrom(messageTopic.getReceiverFrom());
		this.sqlSessionTemplate.update("updateByReceiver", userMessage);		
	}


	@Override
	public void updateMessageBySender(Long sender, MessageTopic messageTopic)
			throws Exception {
		UserMessage userMessage = new UserMessage();
		userMessage.setSender(sender);
		userMessage.setSendTime(messageTopic.getSendTime());
		userMessage.setSenderFrom(messageTopic.getSenderFrom());
		this.sqlSessionTemplate.update("updateBySender", userMessage);		
	}

}
