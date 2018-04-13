package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class NoticeStruc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6695352528237418585L;
	private Long userId;
	private String noticeTime;
	private String url;
	private String text;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
