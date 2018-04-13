package com.winterframework.firefrog.common.mq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.winterframework.modules.spring.exetend.PropertyConfig;

/*@Configuration
public class Config {

	
	
	@PropertyConfig(value = "mq.address")
	protected String rabbitAddress;
	@PropertyConfig(value = "mq.username")
	protected String userName;
	@PropertyConfig(value = "mq.passwd")
	protected String paswd;
	@PropertyConfig(value = "mq.queue.noticeTask")
	protected String noticeQ;
	@Bean
	protected ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = new CachingConnectionFactory();
		((CachingConnectionFactory)connectionFactory).setAddresses(rabbitAddress);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}

}
*/