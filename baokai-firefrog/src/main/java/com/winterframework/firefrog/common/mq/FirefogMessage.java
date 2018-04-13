package com.winterframework.firefrog.common.mq;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import com.winterframework.firefrog.common.util.DataConverterUtil;



public class FirefogMessage extends Message {

	public FirefogMessage(byte[] body, MessageProperties messageProperties) {
		super(body, messageProperties);
		// TODO Auto-generated constructor stub
	}
	public <T> FirefogMessage(T msg) throws UnsupportedEncodingException {
		super(DataConverterUtil.convertObject2Json(msg).getBytes("UTF-8"),new MessageProperties());
		// TODO Auto-generated constructor stub
	}


}
