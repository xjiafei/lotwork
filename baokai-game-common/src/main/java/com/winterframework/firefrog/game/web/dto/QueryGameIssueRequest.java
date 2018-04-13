package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class QueryGameIssueRequest implements Serializable {
	
	private static final long serialVersionUID = -7577683918164293166L;
	
	@NotNull
	private Long lotteryId;
	@NotNull
	private Long ruleId;
	@NotNull
	private Integer status;
	
	public QueryGameIssueRequest(){
		
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

}
