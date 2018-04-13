package com.winterframework.firefrog.mq;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMqService {
	
	private LogUtil logger = new LogUtil(getClass());

	protected Connection conn;

	protected Channel channel;

	protected final String userName;

	protected final String password;

	protected final String hostName;

	protected final int port;

	public static final String QUEUE_NAME = "RabbitMqMonitor";

	public RabbitMqService() {
		Properties properties = new Properties();
		try {
			URL url = ClassLoader.getSystemResource("mq.properties");
			properties.load(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		userName = properties.getProperty("mq.username");
		password = properties.getProperty("mq.passwd");
		hostName = properties.getProperty("mq.ip");
		port =Integer.parseInt(properties.getProperty("mq.port"));
	}
	
	public RabbitMqService(String UserName ,String Passwd, String Ip, String Port) {
		Properties properties = new Properties();
		try {
			URL url = ClassLoader.getSystemResource("mq.properties");
			properties.load(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		userName = UserName;
		password = Passwd;
		hostName = Ip;
		port =Integer.parseInt(Port);
	}
	
	public void sendMsg() throws Exception {
		logger.doLog("sendMsg start");
		connect();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = new Date()+" Connection Msg";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    logger.doLog("Send: '" + message + "'");
		close();
		logger.doLog("sendMsg finish");
	}
	
	public void recevieMsg() throws Exception {
		logger.doLog("recevieMsg start");
		connect();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    String message = new String(delivery.getBody());
	    logger.doLog("Received '" + message + "'");
		close();
		logger.doLog("recevieMsg end");
	}

	protected void connect() {
		if (conn==null||!conn.isOpen()) {
			initConnection();
		}
		if (channel==null||!channel.isOpen()) {
			initChannel();
		}
	}

	protected void close() {
		try {
			if (channel.isOpen()) {
				channel.close();
				logger.doLog(getClass().getSimpleName()+":CHANNEL CLOSE");
			}
			if (conn.isOpen()) {
				conn.close();
				logger.doLog(getClass().getSimpleName()+":CONNECTION CLOSE");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initConnection() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUsername(userName);
			factory.setPassword(password);
			factory.setHost(hostName);
			factory.setPort(port);
			conn = factory.newConnection();
			logger.doLog(getClass().getSimpleName()+":CONNECTION START");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initChannel() {
		try {
			channel = conn.createChannel();
			logger.doLog(getClass().getSimpleName()+":CHANNEL START");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
