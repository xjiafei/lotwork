package com.winterframework.firefrog.user.web.controller.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameGroupReq {
	@JsonProperty("userid")
	private Long userId;
	private Long type;
	private Long awardType;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getAwardType() {
		return awardType;
	}
	public void setAwardType(Long awardType) {
		this.awardType = awardType;
	}
	

}
