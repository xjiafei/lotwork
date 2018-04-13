package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class MessageReplyRequestDTO implements Serializable {

	private static final long serialVersionUID = 8680743282157913065L;

	private long rootId;

	private long parentId;

	private String content;

	private String sendAccount;

	private String receiveAccount;

	public long getRootId() {
		return rootId;
	}

	public void setRootId(long rootId) {
		this.rootId = rootId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
