/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserMessage extends BaseEntity {

	private static final long serialVersionUID = -7376082958319180141L;
	// alias
	public static final String TABLE_ALIAS = "UserMessage";
	public static final String ALIAS_SENDER = "发送者id";
	public static final String ALIAS_SEND_TIME = "发送时间";
	public static final String ALIAS_READ_TIME = "接收时间";
	public static final String ALIAS_SENDER_UNREAD = "收件人未读 0未读 1已读";
	public static final String ALIAS_TITLE = "标题，长度为30个字符以内";
	public static final String ALIAS_CONTENT = "内容，非必填，长度300个字符以内";
	public static final String ALIAS_TYPE = "1：系统消息 2：普通消息";
	public static final String ALIAS_SENDER_ACCOUNT = "发送者账号";
	public static final String ALIAS_RECEIVER_ACCOUNT = "接受者账号";
	public static final String ALIAS_RECEIVER = "接受者";
	public static final String ALIAS_RECEIVERS = " 一对多发送的时候记录所有的账号";
	public static final String ALIAS_MSG_ROUT = "路由";
	public static final String ALIAS_SENDER_FROM = "发送者从那条开始看 -1表示不可见";
	public static final String ALIAS_RECEIVE_FROM = "接受者从那条开始看 -1表示不可见";
	public static final String ALIAS_RECEIVE_UNREAD = "发件人未读 0未读 1已读";

	// date formats

	// columns START
	private Long sender;
	private Date sendTime;
	private Date readTime;
	private Long senderUnread;
	private String title;
	private String content;
	private Long type;
	private String senderAccount;
	private String receiverAccount;
	private Long receiver;
	private String receivers;
	private String msgRout;
	private Long senderFrom;
	private Long receiveFrom;
	private Long receiveUnread;
	private Long NoticeMsgId;

	private Date effectHours;
	private String messagePush;

	// columns END

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public UserMessage() {
	}

	public UserMessage(Long id) {
		this.id = id;
	}

	public void setSender(Long value) {
		this.sender = value;
	}

	public Long getSender() {
		return this.sender;
	}

	public void setSendTime(Date value) {
		this.sendTime = value;
	}

	public Object getSendTime() {
		return this.sendTime;
	}

	public void setReadTime(Date value) {
		this.readTime = value;
	}

	public Object getReadTime() {
		return this.readTime;
	}

	public void setSenderUnread(Long value) {
		this.senderUnread = value;
	}

	public Long getSenderUnread() {
		return this.senderUnread;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	public void setType(Long value) {
		this.type = value;
	}

	public Long getType() {
		return this.type;
	}

	public void setSenderAccount(String value) {
		this.senderAccount = value;
	}

	public String getSenderAccount() {
		return this.senderAccount;
	}

	public void setReceiverAccount(String value) {
		this.receiverAccount = value;
	}

	public String getReceiverAccount() {
		return this.receiverAccount;
	}

	public void setReceiver(Long value) {
		this.receiver = value;
	}

	public Long getReceiver() {
		return this.receiver;
	}

	public void setReceivers(String value) {
		this.receivers = value;
	}

	public String getReceivers() {
		return this.receivers;
	}

	public void setMsgRout(String value) {
		this.msgRout = value;
	}

	public String getMsgRout() {
		return this.msgRout;
	}

	public void setSenderFrom(Long value) {
		this.senderFrom = value;
	}

	public Long getSenderFrom() {
		return this.senderFrom;
	}

	public void setReceiveFrom(Long value) {
		this.receiveFrom = value;
	}

	public Long getReceiveFrom() {
		return this.receiveFrom;
	}

	public void setReceiveUnread(Long value) {
		this.receiveUnread = value;
	}

	public Long getReceiveUnread() {
		return this.receiveUnread;
	}

	public Date getEffectHours() {
		return effectHours;
	}

	public void setEffectHours(Date effectHours) {
		this.effectHours = effectHours;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Sender", getSender())
				.append("SendTime", getSendTime()).append("ReadTime", getReadTime())
				.append("SenderUnread", getSenderUnread()).append("Title", getTitle()).append("Content", getContent())
				.append("Type", getType()).append("SenderAccount", getSenderAccount())
				.append("ReceiverAccount", getReceiverAccount()).append("Receiver", getReceiver())
				.append("Receivers", getReceivers()).append("MsgRout", getMsgRout())
				.append("SenderFrom", getSenderFrom()).append("ReceiveFrom", getReceiveFrom())
				.append("effectHours", this.getEffectHours()).append("ReceiveUnread", getReceiveUnread()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getSender()).append(getSendTime()).append(getReadTime())
				.append(getSenderUnread()).append(getTitle()).append(getContent()).append(getType())
				.append(getSenderAccount()).append(getReceiverAccount()).append(getReceiver()).append(getReceivers())
				.append(getMsgRout()).append(getSenderFrom()).append(getReceiveFrom()).append(getReceiveUnread())
				.append(this.getEffectHours()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserMessage == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserMessage other = (UserMessage) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getSender(), other.getSender())

		.append(getSendTime(), other.getSendTime())

		.append(getReadTime(), other.getReadTime())

		.append(getSenderUnread(), other.getSenderUnread())

		.append(getTitle(), other.getTitle())

		.append(getContent(), other.getContent())

		.append(getType(), other.getType())

		.append(getSenderAccount(), other.getSenderAccount())

		.append(getReceiverAccount(), other.getReceiverAccount())

		.append(getReceiver(), other.getReceiver())

		.append(getReceivers(), other.getReceivers())

		.append(getMsgRout(), other.getMsgRout())

		.append(getSenderFrom(), other.getSenderFrom())

		.append(getReceiveFrom(), other.getReceiveFrom())

		.append(getReceiveUnread(), other.getReceiveUnread()).append(this.getEffectHours(), other.getEffectHours())

		.isEquals();
	}

	public Long getNoticeMsgId() {
		return NoticeMsgId;
	}

	public void setNoticeMsgId(Long noticeMsgId) {
		NoticeMsgId = noticeMsgId;
	}
}
