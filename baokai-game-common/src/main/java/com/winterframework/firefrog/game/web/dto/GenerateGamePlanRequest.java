package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GenerateGamePlanRequest implements Serializable {
	private static final long serialVersionUID = 740825200861241146L;

	private Long lotteryId;
	private Long issueCode;
	
	private List<GamePlanDTO> planList;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public List<GamePlanDTO> getPlanList() {
		return planList;
	}
	public void setPlanList(List<GamePlanDTO> planList) {
		this.planList = planList;
	}
	
}
