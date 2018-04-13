package com.winterframework.firefrog.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.IUserMessageDao;
import com.winterframework.firefrog.user.dao.IUserMessageReplyDao;
import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.entity.User;

@Transactional(readOnly = false, rollbackFor = Exception.class)
public class BaseReplyStrategy {

	@Autowired
	protected IUserMessageDao userMessageDao;
	@Autowired
	protected IUserMessageReplyDao userMessageReplyDao;

	protected long currentId;
	protected MessageTopic topic;

	public BaseReplyStrategy() {

	}

	public void createMessageReply(long replier, long topicId, long parentId, String content) throws Exception {

		topic = userMessageDao.getMessageTopicByTopicId(topicId, replier);

		if (null == topic) {

			throw new RuntimeException("获取MessageTopic异常");
		}

		Message parent = null;
		if (parentId == topicId) {
			parent = topic;
		} else {
			parent = new MessageReply();
			parent.setId(parentId);
		}
		User sender = null;
		User receiver = null;
		List<User> receivers = new ArrayList<User>();
		// 从topic对象中，取出sender、receiver对象，用于在messageReply设置对应值
		if (topic.getSender().getId() == replier) {
			sender = topic.getSender();
			receiver = topic.getReceiver().get(0);
		} else if (topic.getReceiver().get(0).getId() == replier) {
			sender = topic.getReceiver().get(0);
			receiver = topic.getSender();
		}
		receivers.add(receiver);
		MessageReply messageReply = new MessageReply();
		messageReply.setParent(parent);
		messageReply.setRoot(topic);
		messageReply.setContent(content);
		messageReply.setSender(sender);
		messageReply.setReceiver(receivers);
		messageReply.setSendTime(new Date());

		//插入一条回复消息，并返回该回复消息的id
		currentId = userMessageReplyDao.insertMessageReply(messageReply);
		//将主题的更改时间更新为回复消息的发送时间
		topic.setGmtModified(new Date());
	}

	public String getMsgRoute() {

		String msgRoute = topic.getMsgRoute();
		StringBuilder newMsgRoute = new StringBuilder();
		newMsgRoute.append(msgRoute).append(",").append(currentId);
		return newMsgRoute.toString();
	}

}
