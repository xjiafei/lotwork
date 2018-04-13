package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CancelTaskRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3579311081247754731L;
	
	private String planId;
	private String issueCode;
	private Long lotteryId;
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	

}
