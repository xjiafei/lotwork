package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameIssueRuleQueryDTO implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;
	
	private Long lotteryId;
	private String issueRulesName;
	private String ruleStartTime;
	private String ruleEndTime;
	private String openAwardPeriod;
	private Integer ruleStatus;
	private Integer ruleType;
	private Long ruleId;
	private Integer status;//原来的状态
	private String stopStartTime;
	private String stopEndTime;
	
	private List<SalesIssueStrucDTO> salesIssueStrucs;

	public long getLotteryId() {
		return lotteryId;
	}

	public String getIssueRulesName() {
		return issueRulesName;
	}

	public void setIssueRulesName(String issueRulesName) {
		this.issueRulesName = issueRulesName;
	}


	public List<SalesIssueStrucDTO> getSalesIssueStrucs() {
		return salesIssueStrucs;
	}

	public void setSalesIssueStrucs(List<SalesIssueStrucDTO> salesIssueStrucs) {
		this.salesIssueStrucs = salesIssueStrucs;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getRuleStartTime() {
		return ruleStartTime;
	}

	public void setRuleStartTime(String ruleStartTime) {
		this.ruleStartTime = ruleStartTime;
	}

	public String getRuleEndTime() {
		return ruleEndTime;
	}

	public void setRuleEndTime(String ruleEndTime) {
		this.ruleEndTime = ruleEndTime;
	}

	public String getOpenAwardPeriod() {
		return openAwardPeriod;
	}

	public void setOpenAwardPeriod(String openAwardPeriod) {
		this.openAwardPeriod = openAwardPeriod;
	}

	public Integer getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(Integer ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
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

	public String getStopStartTime() {
		return stopStartTime;
	}

	public void setStopStartTime(String stopStartTime) {
		this.stopStartTime = stopStartTime;
	}

	public String getStopEndTime() {
		return stopEndTime;
	}

	public void setStopEndTime(String stopEndTime) {
		this.stopEndTime = stopEndTime;
	}
}
