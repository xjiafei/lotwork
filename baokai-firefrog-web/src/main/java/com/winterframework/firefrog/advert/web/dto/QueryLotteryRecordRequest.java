package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryLotteryRecordRequest implements Serializable{

	private static final long serialVersionUID = 7565663294657611576L;
    
	private String account;
	
	private Date activityStartTime;
	
	private Date activityEndTime;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
}
