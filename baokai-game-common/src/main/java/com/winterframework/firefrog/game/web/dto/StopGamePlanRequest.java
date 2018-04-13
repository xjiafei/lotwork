package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class StopGamePlanRequest implements Serializable {

	private static final long serialVersionUID = -3264626866303197892L;
	@NotNull
	private Long planId;
	@NotNull
	private Long cancelTime;
	@NotNull
	private Integer userType;

	public StopGamePlanRequest() {

	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Long cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
