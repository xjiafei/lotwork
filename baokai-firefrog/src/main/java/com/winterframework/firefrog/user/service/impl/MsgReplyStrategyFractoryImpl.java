package com.winterframework.firefrog.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.service.IMsgReplyStrategy;
import com.winterframework.firefrog.user.service.IMsgReplyStrategyFactory;

@Service("msgReplyStrategyFractory")
public class MsgReplyStrategyFractoryImpl implements IMsgReplyStrategyFactory, ApplicationContextAware {

	private static final long ONE_TO_MANY = -2;
	private static final long ONE_TO_ONE_DELETE = -1;
	private static final long ONE_TO_ONE_NORMAL = 0;

	private static final String ONE_TO_MANY_TOPIC_REPLY_STRATEGY = "oneToManyTopicReplyStrategy";
	private static final String ONE_TO_ONE_NORMAL_TOPIC_REPLY_STRATEGY = "oneToOneNormalTopicReplyStrategy";
	private static final String ONE_TO_ONE_TOPIC_BE_DELELTED_REPLY_STRATEGY = "oneToOneTopicBeDeleltedReplyStrategy";

	private ApplicationContext ctx;

	private static final Map<Long, String> messageProcessorNames = new HashMap<Long, String>();
	static {

		messageProcessorNames.put(ONE_TO_ONE_NORMAL, ONE_TO_ONE_NORMAL_TOPIC_REPLY_STRATEGY);
		messageProcessorNames.put(ONE_TO_ONE_DELETE, ONE_TO_ONE_TOPIC_BE_DELELTED_REPLY_STRATEGY);
		messageProcessorNames.put(ONE_TO_MANY, ONE_TO_MANY_TOPIC_REPLY_STRATEGY);
	}

	@Override
	public IMsgReplyStrategy getMsgReplyStrategy(MessageTopic topic) {

		if (null == topic) {
			return null;
		}

		long num = 0;
		if (topic.getReceiverFrom() == -1) {
			num = -1;
		} else {
			num = topic.getSenderFrom();
		}

		if (num > 0) {
			num = 0;
		}

		return (IMsgReplyStrategy) getProcessor(num);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

	private Object getProcessor(long num) {

		String beanId = messageProcessorNames.get(num);
		return ctx.getBean(beanId);
	}

}
