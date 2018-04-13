package com.winterframework.firefrog.notice.entity;

public class NoticeMqMsg {
	
	private Long noticeId;
	private Long sendUserId;
	private String sendAccount;
	private Long receiveId;
	private String content;
	private String messagePush;

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

}
