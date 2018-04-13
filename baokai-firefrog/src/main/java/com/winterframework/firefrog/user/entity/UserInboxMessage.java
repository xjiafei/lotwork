package com.winterframework.firefrog.user.entity;

import java.util.Date;

public class UserInboxMessage {

	private Long groupId;

	private Long ownUserId;

	private Long talkUserId;
	
	private String talkUserAccount;

	private Long unreadCount;

	private Long sendUser;

	private String sendAccount;

	private String content;

	private Date createDate;
	
	private Date lastReadTime;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getOwnUserId() {
		return ownUserId;
	}

	public void setOwnUserId(Long ownUserId) {
		this.ownUserId = ownUserId;
	}

	public Long getTalkUserId() {
		return talkUserId;
	}

	public void setTalkUserId(Long talkUserId) {
		this.talkUserId = talkUserId;
	}

	public Long getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Long unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Long getSendUser() {
		return sendUser;
	}

	public void setSendUser(Long sendUser) {
		this.sendUser = sendUser;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastReadTime() {
		return lastReadTime;
	}

	public void setLastReadTime(Date lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

	public String getTalkUserAccount() {
		return talkUserAccount;
	}

	public void setTalkUserAccount(String talkUserAccount) {
		this.talkUserAccount = talkUserAccount;
	}

}
