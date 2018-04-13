package com.winterframework.firefrog.advert.web.dto;

public class AdNoticeUpdateResult {

	public static final int SUCCESS = 1;

	public static final int FAIL = 0;
	
	public static final int CONTENT_TOO_LONG = 2;

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
