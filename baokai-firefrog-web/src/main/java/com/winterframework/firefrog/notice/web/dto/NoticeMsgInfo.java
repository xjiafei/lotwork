package com.winterframework.firefrog.notice.web.dto;

public class NoticeMsgInfo {
	
	private Long id;
	
	private String accept;
	
	private String acceptUser;
	
	private String acceptGroup;
	private String receives;
	
	private String sentTime;
	
	private String expiredTime;
	
	private String operator;
	
	private String title;
	
	private String content;
	
	private Long repayCount;
	
	private String messagePush;
	
	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public String getReceives() {
		return receives;
	}

	public void setReceives(String receives) {
		this.receives = receives;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptUser() {
		return acceptUser;
	}

	public void setAcceptUser(String acceptUser) {
		this.acceptUser = acceptUser;
	}

	public String getAcceptGroup() {
		return acceptGroup;
	}

	public void setAcceptGroup(String acceptGroup) {
		this.acceptGroup = acceptGroup;
	}

	public String getSentTime() {
		return sentTime;
	}

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRepayCount() {
		return repayCount;
	}

	public void setRepayCount(Long repayCount) {
		this.repayCount = repayCount;
	}
}
