package com.winterframework.firefrog.user.service;

import com.winterframework.firefrog.user.entity.MessageTopic;

public interface IMsgReplyStrategyFactory {
	public IMsgReplyStrategy getMsgReplyStrategy(MessageTopic topic);
}
