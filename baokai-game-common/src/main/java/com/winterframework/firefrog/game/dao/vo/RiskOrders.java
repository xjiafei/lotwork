package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;
import java.util.List;

public class RiskOrders implements Serializable {

	private static final long serialVersionUID = -1385002756465990008L;
	
	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private Long userid;	
	private String account;	
	private Long countWins;	
	private Long issueWinsRatio;
	private Long orderwarnContinuousWins;	
	private Long continuousWinsTimes;
	private Long maxslipWins;	
	private List<RiskOrderDetail> orderDetails;
	
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
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
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
	public Long getContinuousWinsTimes() {
		return continuousWinsTimes;
	}
	public void setContinuousWinsTimes(Long continuousWinsTimes) {
		this.continuousWinsTimes = continuousWinsTimes;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public List<RiskOrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<RiskOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
