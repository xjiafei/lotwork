package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class GameIssueRuleQueryResponse implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;
	private String issueRulesName;
	private Long ruleStartTime;
	private Long ruleEndTime;
	private String openAwardPeriod;
	private Integer ruleStatus;
	private Integer ruleType;
	private Long ruleId;
	private Long stopStartTime;
	private Long stopEndTime;
	private Long seriesIssueCode;
	private List<SalesIssueStruc> salesIssueStrucs;

	public long getLotteryId() {
		return lotteryId;
	}
	
	

	public Long getSeriesIssueCode() {
		return seriesIssueCode;
	}



	public void setSeriesIssueCode(Long seriesIssueCode) {
		this.seriesIssueCode = seriesIssueCode;
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

	public List<SalesIssueStruc> getSalesIssueStrucs() {
		return salesIssueStrucs;
	}

	public void setSalesIssueStrucs(List<SalesIssueStruc> salesIssueStrucs) {
		this.salesIssueStrucs = salesIssueStrucs;
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

	public Long getStopStartTime() {
		return stopStartTime;
	}

	public void setStopStartTime(Long stopStartTime) {
		this.stopStartTime = stopStartTime;
	}

	public Long getStopEndTime() {
		return stopEndTime;
	}

	public void setStopEndTime(Long stopEndTime) {
		this.stopEndTime = stopEndTime;
	}

}
