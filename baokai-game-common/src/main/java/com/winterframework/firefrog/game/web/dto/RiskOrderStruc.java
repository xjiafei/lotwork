package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: RiskOrderStruc 
* @Description: 5.2.29	RISK_ORDER_STRUC（风险方案基本结构）CSI029
* @author Richard
* @date 2013-10-12 上午9:50:47 
*
 */
public class RiskOrderStruc implements Serializable {

	private static final long serialVersionUID = 7484118503897326726L;

	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private Long userId;
	private String account;
	private Long countWins;
	private Long issueWinsRatio;
	private Long orderwarnContinuousWins;
	private Long continuousWinsTimes;
	private Long maxslipWins;
	private Integer status;
	private List<RiskOrderDetailStruc> riskOrderDetailStrucs;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getCountWins() {
		return countWins;
	}
	public void setCountWins(Long countWins) {
		this.countWins = countWins;
	}
	public Long getIssueWinsRatio() {
		return issueWinsRatio;
	}
	public void setIssueWinsRatio(Long issueWinsRatio) {
		this.issueWinsRatio = issueWinsRatio;
	}
	public Long getOrderwarnContinuousWins() {
		return orderwarnContinuousWins;
	}
	public void setOrderwarnContinuousWins(Long orderwarnContinuousWins) {
		this.orderwarnContinuousWins = orderwarnContinuousWins;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<RiskOrderDetailStruc> getRiskOrderDetailStrucs() {
		return riskOrderDetailStrucs;
	}
	public void setRiskOrderDetailStrucs(List<RiskOrderDetailStruc> riskOrderDetailStrucs) {
		this.riskOrderDetailStrucs = riskOrderDetailStrucs;
	}
	public Long getContinuousWinsTimes() {
		return continuousWinsTimes;
	}
	public void setContinuousWinsTimes(Long continuousWinsTimes) {
		this.continuousWinsTimes = continuousWinsTimes;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	
}
