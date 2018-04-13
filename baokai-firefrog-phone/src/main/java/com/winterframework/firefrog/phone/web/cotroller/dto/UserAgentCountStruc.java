package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class UserAgentCountStruc {

	
	private Long userId;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date startTime;
//	@JsonSerialize(using = FirefrogDateSerializer.class)  
//	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
//	private Date endTime;
	private Long type;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
//	public Date getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	
}
