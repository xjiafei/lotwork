package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class NoticeMsg extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String account;

	private String title;

	private String content;

	private Date sendTime;
	
	private String msgPush;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgPush() {
		return msgPush;
	}

	public void setMsgPush(String msgPush) {
		this.msgPush = msgPush;
	}

}
