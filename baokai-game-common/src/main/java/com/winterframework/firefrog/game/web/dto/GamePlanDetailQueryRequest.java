package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GamePlanDetailQueryRequest implements Serializable {

	private static final long serialVersionUID = 335864117514228128L;

	private Long planid;

	private String planCode;

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
}
