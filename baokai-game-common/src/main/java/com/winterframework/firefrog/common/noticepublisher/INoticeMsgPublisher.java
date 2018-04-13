package com.winterframework.firefrog.common.noticepublisher;

import java.util.Map;

/** 
*  向mq中添加信息
*/
public interface INoticeMsgPublisher {
	void addMessageToMq(Long userId, String account, String queueName, Long taskId, Map<String, String> paramMap)
			throws Exception;

	void addMessageToMq(Long userId, String account, String queueName, Long taskId) throws Exception;

}
