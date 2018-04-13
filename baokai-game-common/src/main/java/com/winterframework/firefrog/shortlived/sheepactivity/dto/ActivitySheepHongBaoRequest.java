package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.Date;

public class ActivitySheepHongBaoRequest {
	private String userName;
	private Long status;//状态
	private Long updateStatus;//状态	
	private Long channel;//来源		
	private Date signTimeBegin; //报名时间 			SIGN_TIME
	private Date signTimEnd; //报名时间 			SIGN_TIME
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(Long updateStatus) {
		this.updateStatus = updateStatus;
	}
	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public Date getSignTimeBegin() {
		return signTimeBegin;
	}
	public void setSignTimeBegin(Date signTimeBegin) {
		this.signTimeBegin = signTimeBegin;
	}
	public Date getSignTimEnd() {
		return signTimEnd;
	}
	public void setSignTimEnd(Date signTimEnd) {
		this.signTimEnd = signTimEnd;
	}
	
	
	
}
