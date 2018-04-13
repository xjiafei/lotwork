package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class CommonOrSpecialGameIssueRuleAddOrUpdateRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;
	private String issueRulesName;
	@NotNull
	private Long ruleStartTime;
	private Long ruleEndTime;
	private Long ruleId;
	private Integer ruleType;
	private String openAwardPeriod;
	private List<SalesIssueStruc> salesIssueStrucs;
	private Integer operationType;//1新增，2 修改

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getIssueRulesName() {
		return issueRulesName;
	}

	public void setIssueRulesName(String issueRulesName) {
		this.issueRulesName = issueRulesName;
	}

	public Long getRuleStartTime() {
		return ruleStartTime;
	}

	public void setRuleStartTime(Long ruleStartTime) {
		this.ruleStartTime = ruleStartTime;
	}

	public Long getRuleEndTime() {
		return ruleEndTime;
	}

	public void setRuleEndTime(Long ruleEndTime) {
		this.ruleEndTime = ruleEndTime;
	}

	public String getOpenAwardPeriod() {
		return openAwardPeriod;
	}

	public void setOpenAwardPeriod(String openAwardPeriod) {
		this.openAwardPeriod = openAwardPeriod;
	}

	public List<SalesIssueStruc> getSalesIssueStrucs() {
		return salesIssueStrucs;
	}

	public void setSalesIssueStrucs(List<SalesIssueStruc> salesIssueStrucs) {
		this.salesIssueStrucs = salesIssueStrucs;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

}
