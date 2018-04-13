package com.winterframework.firefrog.user.entity;

import java.util.Date;
import java.util.List;

public class Message {

	private long id;

	private User sender;

	private List<User> receiver;

	private Date sendTime;

	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List<User> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<User> receiver) {
		this.receiver = receiver;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
