package com.winterframework.firefrog.user.service;

public interface IMsgReplyStrategy {
	public void replyMessage(long replier, long topicId, long parentId, String content) throws Exception;
}
