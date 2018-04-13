package com.winterframework.firefrog.notice.job;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rabbitmq.client.Channel;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao;
import com.winterframework.firefrog.notice.entity.MQMsg;
import com.winterframework.firefrog.notice.service.INoticeMsgService;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.service.impl.NoticeTaskServiceImpl;
import com.winterframework.modules.spring.exetend.PropertyConfig;

//@Configuration
@Import(NoticeTaskServiceImpl.class)
public class AdminMessageListenerRun {

	private static Logger logger = LoggerFactory.getLogger(AdminMessageListenerRun.class);
	@PropertyConfig (value = "mq.sender.Consumers")
	protected String rabbitConsumers;
	@PropertyConfig(value = "mq.queue.admin.message")
	private String adminMsgQ;
	@Autowired(required=false)
	private CachingConnectionFactory cachingConnectionFactory;
	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;
	@Resource(name = "userNoticeTaskDaoImpl")
	private IUserNoticeTaskDao userNoticeDao;

	@Bean
	public Queue adminMsg0Q() {
		Queue q = new Queue(getQueueName(0), true);
		return q;
	}
	@Bean
	public Queue adminMsg1Q() {
		Queue q = new Queue(getQueueName(1), true);
		return q;
	}
	@Bean
	public Queue adminMsg2Q() {
		Queue q = new Queue(getQueueName(2), true);
		return q;
	}
	@Bean
	public Queue adminMsg3Q() {
		Queue q = new Queue(getQueueName(3), true);
		return q;
	}
	@Bean
	public Queue adminMsg4Q() {
		Queue q = new Queue(getQueueName(4), true);
		return q;
	}
	
	private String getQueueName(int id){
		return adminMsgQ+id;
	}
	
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer0() {
		return createContainer(getQueueName(0));
	}
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer1() {
		return createContainer(getQueueName(1));
	}
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer2() {
		return createContainer(getQueueName(2));
	}
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer3() {
		return createContainer(getQueueName(3));
	}
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer4() {
		return createContainer(getQueueName(4));
	}
	
	private SimpleMessageListenerContainer createContainer(String queueName){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(cachingConnectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(Integer.valueOf(rabbitConsumers));
		container.setQueues(new Queue(queueName,true));
		container.setMessageListener(msgQListener());
		return container;
	}

	@Resource(name = "noticeMsgServiceImpl")
	private INoticeMsgService noticeMsgServiceImpl;

	public ChannelAwareMessageListener msgQListener() {
		return new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) {
				logger.debug("received: " + message);

				MQMsg param = (MQMsg) DataConverterUtil.convertJson2Object(message, MQMsg.class);
				String content = param.getParamMap().get("content");
				String receiveId = param.getParamMap().get("receiveId");
				String noticeId = param.getParamMap().get("noticeId");
				Long sendUserId = param.getUserId();
				String sendAccount = param.getUserName();
				try {
					noticeMsgServiceImpl.sendMsg(sendUserId,sendAccount,Long.parseLong(receiveId),content);
					saveNoticeMsgPush(Long.parseLong(receiveId),noticeId);
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					logger.debug("finish sendMsg channel.basicAck");
				} catch (Exception e) {
					try {
						logger.error("repeat send notice.", e);
						noticeMsgServiceImpl.sendMsg(sendUserId,sendAccount,Long.parseLong(receiveId),content);
						saveNoticeMsgPush(Long.parseLong(receiveId),noticeId);
						channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					} catch (Exception e1) {
						try {
							channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						logger.error("send notice error,drop it.", e);
					}
				}
			}
			private void saveNoticeMsgPush(Long receiveId,String noticeId){
				try {
					if(noticeId!=null && !noticeId.equals("0")){
						noticeMsgServiceImpl.saveNoticeMsgPush(receiveId,Long.parseLong(noticeId));
					}
				} catch (Exception e) {
					logger.error("savePushNoticeMsg error.", e);
				}
			}
		};
	}
}
