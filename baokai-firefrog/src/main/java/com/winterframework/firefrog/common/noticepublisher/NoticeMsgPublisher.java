package com.winterframework.firefrog.common.noticepublisher;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.mq.FirefogMessage;
import com.winterframework.firefrog.notice.entity.MQMsg;
import com.winterframework.firefrog.notice.entity.NoticeMqMsg;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.vo.ImGroupMessageVo;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
*  向MQ队列中添加信息
*/

@Service("noticeMsgPublisher")
public class NoticeMsgPublisher implements INoticeMsgPublisher {
	
	private static Logger logger = LoggerFactory.getLogger(NoticeMsgPublisher.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@PropertyConfig(value="static.resources.path")
	private String staticURL;
	@PropertyConfig(value = "platform.name")
	private String platformName;
	
	/** 
	* MQ的队列名称 
	*/
	@PropertyConfig(value = "mq.queue.noticeTask")
	private String queueName;
	
	@PropertyConfig(value = "mq.queue.im")
	private String imQueueName;

	/** 
	 * 将信息添加到MQ队列中
	*/
	public void addMessageToMq(Long userId, String account, NoticeTaskEnum task, Map<String, String> paramMap)
			throws Exception {
		if(paramMap!=null)
		paramMap.put("platform", platformName);
		paramMap.put("path_img", staticURL);
		this.addMessageToMq(userId, account, (long) task.getIndex(), paramMap);
	}

	/** 
	 * 创建消息体
	*/
	@Override
	public void addMessageToMq(Long userId, String account, Long taskId, Map<String, String> paramMap) throws Exception {
		if(paramMap!=null)
			paramMap.put("platform", platformName);
			paramMap.put("path_img", staticURL);
		MQMsg mqMsg = createMQMsg(userId, account, taskId, paramMap);
	
		rabbitTemplate.send(queueName,new FirefogMessage(mqMsg));
	}

	@Override
	public void addMessageToMq(Long userId, String account, Long taskId) throws Exception {
		this.addMessageToMq(userId, account, taskId, new HashMap<String, String>());
	}

	@Override
	public void addMessageToMq(Long userId, String account, NoticeTaskEnum task) throws Exception {
		this.addMessageToMq(userId, account, task, new HashMap<String, String>());
	}
	
	@Override
	public void addMessageToMq(BaseUser user, NoticeTaskEnum task, Map<String, String> paramMap) throws Exception{
		if(paramMap!=null)
			paramMap.put("platform", platformName);
			paramMap.put("path_img", staticURL);
		this.addMessageToMq(user.getId(),user.getAccount(), task, paramMap);
		
	}

	private MQMsg createMQMsg(Long userId, String account, Long taskId, Map<String, String> paramMap) {
		if(paramMap!=null)
			paramMap.put("platform", platformName);
			paramMap.put("path_img", staticURL);
		MQMsg mqMsg = new MQMsg(userId, account, taskId, paramMap);
		return mqMsg;
	}

	@Override
	public void addMessageToImMq(Long userId, String account,
			ImGroupMessageVo msgVo) throws Exception {
		logger.debug("addMessageToImMq,userId:"+userId+",msg:"+msgVo.getContent());
		rabbitTemplate.send(imQueueName,new FirefogMessage(msgVo));
	}

	@Override
	public void addMessageToMq(String queueName,NoticeMqMsg noticeMqMsg) throws Exception{
		Long sendUserId = noticeMqMsg.getSendUserId();
		String sendAccount = noticeMqMsg.getSendAccount();
		Long receiveId = noticeMqMsg.getReceiveId();
		String content = noticeMqMsg.getContent();
		String messagePush = noticeMqMsg.getMessagePush();
		Long noticeId = noticeMqMsg.getNoticeId();
		logger.debug("addMessageToMq,queueName:"+queueName+",userId:"+sendUserId+",receiveId:"+receiveId+",content:"+content);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("receiveId",receiveId.toString());
		paramMap.put("content", content);
		if(noticeId!=null){
			paramMap.put("noticeId", String.valueOf(noticeId));
		}
		paramMap.put("messagePush", messagePush);
		MQMsg mqMsg = new MQMsg(sendUserId, sendAccount, 0L, paramMap );
		rabbitTemplate.send(queueName,new FirefogMessage(mqMsg));
	}

}
