package com.winterframework.firefrog.common.noticepublisher;

import java.util.Map;

import com.winterframework.firefrog.notice.entity.NoticeMqMsg;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.vo.ImGroupMessageVo;

/** 
*  向mq中添加信息
*/
public interface INoticeMsgPublisher {
	void addMessageToMq(BaseUser baseUser,NoticeTaskEnum task, Map<String, String> paramMap) throws Exception;
	void addMessageToMq(Long userId, String account, NoticeTaskEnum task) throws Exception;
	
	void addMessageToMq(Long userId, String account, Long taskId, Map<String, String> paramMap)
			throws Exception;

	void addMessageToMq(Long userId, String account, Long taskId) throws Exception;
	void addMessageToImMq(Long userId, String account, ImGroupMessageVo msgVo) throws Exception;
	void addMessageToMq(String queueName, NoticeMqMsg noticeMqMsg) throws Exception;

}
