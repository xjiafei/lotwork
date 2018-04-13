package com.winterframework.firefrog.common.noticepublisher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.mq.FirefogMessage;


/** 
*  向MQ队列中添加信息
*/

@Service("noticeMsgPublisher")
@Lazy(true)
public class NoticeMsgPublisher implements INoticeMsgPublisher {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	

	/** 
	 * 创建消息体
	*/
	@Override
	public void addMessageToMq(Long userId, String account, String queueName, Long taskId, Map<String, String> paramMap)
			throws Exception {
		MQMsg mqMsg = createMQMsg(userId, account, taskId, paramMap);

		rabbitTemplate.send(queueName, new FirefogMessage(mqMsg));
	}

	@Override
	public void addMessageToMq(Long userId, String account, String queueName, Long taskId) throws Exception {
		this.addMessageToMq(userId, account, queueName, taskId, new HashMap<String, String>());
	}

	private MQMsg createMQMsg(Long userId, String account, Long taskId, Map<String, String> paramMap) {
		MQMsg mqMsg = new MQMsg(userId, account, taskId, paramMap);
		return mqMsg;
	}
}
