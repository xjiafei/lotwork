package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class ApplyLevelRecycleResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 执行状态: SUCCESS | ERROR
	 */
	private String status;
	
	/**
	 * 回传讯息
	 */
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
