package com.winterframework.firefrog.common.email.service.impl;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;
import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.IMailSender;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

//@Configuration
public class DefaultConfig {
	private static Logger logger = LoggerFactory.getLogger(DefaultConfig.class);
	@PropertyConfig (value = "mq.sender.Consumers")
	protected String rabbitConsumers;
	@PropertyConfig(value = "mq.address")
	protected String rabbitAddress;
	@PropertyConfig(value = "mq.username")
	protected String userName;
	@PropertyConfig(value = "mq.passwd")
	protected String paswd;
	@PropertyConfig(value = "mq.queue.name")
	protected String mailQ;
	@Autowired(required=false)
	private CachingConnectionFactory cachingConnectionFactory;

	@Bean
	public Queue mailQ() {
		Queue q = new Queue(mailQ, true);
		return q;
	}

	@Bean
	public SimpleMessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(cachingConnectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(Integer.valueOf(rabbitConsumers));
		container.setQueues(new Queue(mailQ, true));
		container.setMessageListener(mailListener());
		return container;
	}

	@Resource(name = "MailSender")
	private IMailSender mailSender;
	@Bean
	public ChannelAwareMessageListener mailListener() {
		return new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) {	

				logger.debug("received: " + message);

				EmailInfo email = (EmailInfo) DataConverterUtil.convertJson2Object(message, EmailInfo.class);
				try {
					mailSender.sendMail(email);
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

				} catch (Exception e) {
					try {
						logger.error("repeat send mail.", e);
						
						mailSender.sendMail(email);
						channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

					} catch (Exception e1) {
						logger.error("send email error,drop it.", e);
					}
				}
			}
		};
	}
}
