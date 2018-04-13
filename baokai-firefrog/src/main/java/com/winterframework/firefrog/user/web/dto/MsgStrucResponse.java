package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class MsgStrucResponse implements Serializable {

	private static final long serialVersionUID = -3679540802659887050L;

	private Long id;
	private Long sender;
	private Long sendTime;
	private Long receiveTime;
	private Integer isRead;
	private Long parentId;
	private String title;
	private String content;
	private Integer type;
	private String sendAccount;
	private String receiveAccount;
	private String owner;
	private String receives;
	private Integer msgType;
	private String sendMsgRout;
	private Long sendFrom;
	private Long receiveFrom;
	private Long gmtModified;
	private String messagePush;

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public MsgStrucResponse() {

	}

	public Long getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

	public String getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getReceives() {
		return receives;
	}

	public void setReceives(String receives) {
		this.receives = receives;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getSendMsgRout() {
		return sendMsgRout;
	}

	public void setSendMsgRout(String sendMsgRout) {
		this.sendMsgRout = sendMsgRout;
	}

	public Long getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(Long sendFrom) {
		this.sendFrom = sendFrom;
	}

	public Long getReceiveFrom() {
		return receiveFrom;
	}

	public void setReceiveFrom(Long receiveFrom) {
		this.receiveFrom = receiveFrom;
	}

}
