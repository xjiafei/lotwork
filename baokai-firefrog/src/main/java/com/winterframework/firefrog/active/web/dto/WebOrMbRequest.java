package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;

public class WebOrMbRequest implements Serializable{
	
	private String token;
	private Long userId;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
