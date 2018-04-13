package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserMsgDelResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6342472125508585194L;
	private String status;
	private Long messageType;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getMessageType() {
		return messageType;
	}
	public void setMessageType(Long messageType) {
		this.messageType = messageType;
	}
	
}
