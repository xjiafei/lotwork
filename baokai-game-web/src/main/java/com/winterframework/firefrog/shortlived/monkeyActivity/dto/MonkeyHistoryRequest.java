package com.winterframework.firefrog.shortlived.monkeyActivity.dto;

import java.util.Date;

public class MonkeyHistoryRequest {

	private Date startDate;
	private Date endDate;
	private Long userId;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
