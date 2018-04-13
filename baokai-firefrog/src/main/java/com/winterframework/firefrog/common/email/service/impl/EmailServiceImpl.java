package com.winterframework.firefrog.common.email.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.service.IEmailService;
import com.winterframework.firefrog.common.mq.FirefogMessage;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("emailServiceImpl")
public class EmailServiceImpl implements IEmailService {

	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@PropertyConfig(value = "mq.queue.name")
	private String QUEUE_NAME;

	@Override
	public void sendEmail(EmailInfo email) throws Exception {
		try {
			rabbitTemplate.send(QUEUE_NAME,new FirefogMessage(email));
			logger.info("The email to " + email.getAddress() + " is published to mem queue.");
		} catch (Exception e) {
			logger.error("send email error.", e);
			throw e;
		}
	}
}
