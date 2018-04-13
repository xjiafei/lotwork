package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameIssueRuleDTO implements Serializable {

	private static final long serialVersionUID = -7458257627786787586L;

	private Long lotteryId;
	private Long ruleId;
	private Integer status;
	private Integer ruleType;
	
	public GameIssueRuleDTO(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}
	
}
