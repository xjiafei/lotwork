package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;


public class SimpleInitDataResponse implements Serializable {

	private static final long serialVersionUID = 2223621014353387825L;
	
	private String nowTime;//	当前时间
	private Issue issue;	//当前奖期资料
	private String lastOpenCode;//	上期号码
	private String lastWebIssueCode;//	上期期号
	private Double backOutStartFee;//金额超过此数目才收手续费
	private Double backOutRadio;//	手续费收的比例
	private Integer bonusGroupStatus;//玩家是否設定獎金組0:未設 1:已設
	private List<UserAwardStruc> awardGroups;
	public Integer getBonusGroupStatus() {
		return bonusGroupStatus;
	}
	public List<UserAwardStruc> getAwardGroups() {
		return awardGroups;
	}
	public void setAwardGroups(List<UserAwardStruc> awardGroups) {
		this.awardGroups = awardGroups;
	}
	public void setBonusGroupStatus(Integer bonusGroupStatus) {
		this.bonusGroupStatus = bonusGroupStatus;
	}
	public Double getBackOutStartFee() {
		return backOutStartFee;
	}
	public void setBackOutStartFee(Double backOutStartFee) {
		this.backOutStartFee = backOutStartFee;
	}
	public Double getBackOutRadio() {
		return backOutRadio;
	}
	public void setBackOutRadio(Double backOutRadio) {
		this.backOutRadio = backOutRadio;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	public String getLastOpenCode() {
		return lastOpenCode;
	}
	public void setLastOpenCode(String lastOpenCode) {
		this.lastOpenCode = lastOpenCode;
	}
	public String getLastWebIssueCode() {
		return lastWebIssueCode;
	}
	public void setLastWebIssueCode(String lastWebIssueCode) {
		this.lastWebIssueCode = lastWebIssueCode;
	}
	
}
