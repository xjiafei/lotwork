/**   
* @Title: MessageListenerRun.java 
* @Package com.winterframework.firefrog.notice.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-1-14 下午5:24:58 
* @version V1.0   
*/
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
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.service.INoticeMsgService;
import com.winterframework.firefrog.notice.service.INoticeTaskService;
import com.winterframework.firefrog.notice.service.impl.NoticeTaskServiceImpl;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: MessageListenerRun 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-1-14 下午5:24:58 
*  
*/
//@Configuration
@Import(NoticeTaskServiceImpl.class)
public class MessageListenerRun {

	private static Logger logger = LoggerFactory.getLogger(MessageListenerRun.class);
	@PropertyConfig (value = "mq.sender.Consumers")
	protected String rabbitConsumers;
	@PropertyConfig(value = "mq.queue.message")
	protected String msgQ;
	@PropertyConfig(value = "mq.queue.noticeTask")
	protected String noticeQ;
	@PropertyConfig(value = "mq.queue.admin.message")
	private String adminMsgQ;
	@Autowired(required=false)
	private CachingConnectionFactory cachingConnectionFactory;
	@Resource(name = "noticeTaskServiceImpl")
	private INoticeTaskService noticeTaskServiceImpl;
	@Resource(name = "userNoticeTaskDaoImpl")
	private IUserNoticeTaskDao userNoticeDao;

	@Bean
	public Queue msgQ() {
		Queue q = new Queue(msgQ, true);
		return q;
	}
	@Bean
	public Queue adminMsgQ() {
		Queue q = new Queue(adminMsgQ, true);
		return q;
	}
	@Bean
	public Queue noticeQ() {
		Queue q = new Queue(noticeQ, true);
		return q;
	}

	@Bean
	public SimpleMessageListenerContainer noticeListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(cachingConnectionFactory);
		container.setConcurrentConsumers(Integer.valueOf(rabbitConsumers));
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setQueues(noticeQ());
		container.setMessageListener(noticeListener());
		return container;
	}

	@Bean
	public ChannelAwareMessageListener noticeListener() {
		return new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) {
				logger.debug("received: " + message);

				MQMsg taskPram = (MQMsg) DataConverterUtil.convertJson2Object(message, MQMsg.class);
				try {
				
						//如果不为null，并且激活状态为有效的话
					noticeTaskServiceImpl.sendNoticeTask(null, taskPram);

					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

				} catch (Exception e1) {
					try {
						channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
					} catch (Exception e) {
						logger.debug("send notice task error", e);
					}
					logger.error("send notice task error", e1);
				}

			}
		};
	}

	@Bean
	public SimpleMessageListenerContainer msgListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(cachingConnectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(Integer.valueOf(rabbitConsumers));
		container.setQueues(msgQ());
		container.setMessageListener(msgQListener());
		return container;
	}
	
	@Bean
	public SimpleMessageListenerContainer adminMsgListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(cachingConnectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(Integer.valueOf(rabbitConsumers));
		container.setQueues(adminMsgQ());
		container.setMessageListener(msgQListener());
		return container;
	}

	@Resource(name = "noticeMsgServiceImpl")
	private INoticeMsgService noticeMsgServiceImpl;

	@Bean
	public ChannelAwareMessageListener msgQListener() {
		return new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) {
				logger.info("received: " + message);

				NoticeMsg notice = (NoticeMsg) DataConverterUtil.convertJson2Object(message, NoticeMsg.class);
				try {
					noticeMsgServiceImpl.sendMsgIntoMq(notice);
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					logger.info("finish sendMsg channel.basicAck");
				} catch (Exception e) {
					try {
						logger.error("repeat send notice.", e);
						noticeMsgServiceImpl.sendMsgIntoMq(notice);
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
		};
	}
}
