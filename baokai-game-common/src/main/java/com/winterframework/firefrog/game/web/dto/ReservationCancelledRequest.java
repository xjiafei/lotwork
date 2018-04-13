package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ReservationCancelledRequest implements Serializable {

	private static final long serialVersionUID = 2705674811881024070L;
	@NotNull
	private Long planId;
	@NotNull
	private Long issueCode;

	private Integer userType;
	
	private Long lotteryId;
	
	public ReservationCancelledRequest() {

	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
}
