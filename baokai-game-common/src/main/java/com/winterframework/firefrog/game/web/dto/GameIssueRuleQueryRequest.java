package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class GameIssueRuleQueryRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;
	private Long ruleId;
	private Integer ruleStatus;

	public long getLotteryId() {
		return lotteryId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(Integer ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

}
