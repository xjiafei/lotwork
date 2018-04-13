package com.winterframework.firefrog.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.service.IMsgReplyStrategy;

@Service("oneToOneNormalTopicReplyStrategy")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class OneToOneNormalTopicReplyStrategyImpl extends BaseReplyStrategy implements IMsgReplyStrategy {

	@Override
	public void replyMessage(long replier, long topicId, long parentId, String content) throws Exception {

		createMessageReply(replier, topicId, parentId, content);

		if (replier == topic.getSender().getId()) {
			// 若回复者为主题的发送者，将主题的接收者阅读状态置为未读
			topic.setReceiverRead(false);
		} else if (replier == topic.getReceiver().get(0).getId()) {
			// 若回复者为主题的接收者，将主题的发送者阅读状态置为未读
			topic.setSenderRead(false);
		}

		topic.setMsgRoute(getMsgRoute());
		if(topic.getSenderFrom()==-2L){
			topic.setSenderFrom(topic.getId());
		}
		userMessageDao.updateMessage(topic);

	}

}
