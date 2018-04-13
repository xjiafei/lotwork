package com.winterframework.firefrog.user.entity;

public class UserRecycleResult {
	
	public static int STATUS_SUCCESS = 0;
	
	public static int STATUS_FAIL = -1;
	
	private Integer status;

	private String message;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
