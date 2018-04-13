package com.winterframework.firefrog.shortlived.sheepactivity.dto;

public class ActivitySheepUserHongBaoRequest {
	private Long userId;
	
//	private String userName;
	
	private Long channel;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}
	
}
