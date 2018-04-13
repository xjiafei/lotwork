package com.winterframework.firefrog.game.service.bean;

import java.math.BigDecimal;

public class GameWarnServiceBean {
	private Long lotteryid;
	private Long issueCode;
	private Long userid;

	/**
	 * 一期总共中奖金额
	 */
	private Long issueUserCountWin = 0L;
	/**
	 * 订单总共购买金额
	 */
	private Long issueUserTotamount = 0L;
	/**
	 * 一期定投比
	 */
	private Long issueUserWinsRatio = 0L;
	/**
	 * 单注最大中奖金额
	 */
	Long maxSlipWins = 0L;
	private Long continuousWinsIssue =0L;	
	private Long continuousWinsTimes =0L;
	
	private Long isWarn = 0L;
	
	public GameWarnServiceBean(){
		
	}
	
	public GameWarnServiceBean(Long lotteryid, Long issueCode, Long userid) {
		super();
		this.lotteryid = lotteryid;
		this.issueCode = issueCode;
		this.userid = userid;
	}



	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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
	public Long getIssueUserCountWin() {
		return issueUserCountWin;
	}
	public void setIssueUserCountWin(Long issueUserCountWin) {
		this.issueUserCountWin = issueUserCountWin;
	}
	public void addIssueUserCountWin(Long issueUserCountWin) {
		this.issueUserCountWin += issueUserCountWin;
	}
	public Long getIssueUserTotamount() {
		return issueUserTotamount;
	}
	public void setIssueUserTotamount(Long issueUserTotamount) {
		this.issueUserTotamount = issueUserTotamount;
	}
	public void addIssueUserTotamount(Long issueUserTotamount) {
		this.issueUserTotamount += issueUserTotamount;
	}
	public Long getIssueUserWinsRatio() {
		if(issueUserTotamount == 0){
			return issueUserWinsRatio;
		}else{
			BigDecimal a = new BigDecimal(issueUserCountWin*10000);
			BigDecimal b = new BigDecimal(issueUserTotamount);
			BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_HALF_EVEN);
			return c.longValue();
		}		
	}
	public void setIssueUserWinsRatio(Long issueUserWinsRatio) {
		this.issueUserWinsRatio = issueUserWinsRatio;
	}
	/**
	 * 取得单注最大中奖金额
	 */
	public Long getMaxSlipWins() {
		return maxSlipWins;
	}
	/**
	 * 设定单注最大中奖金额
	 */
	public void setMaxSlipWins(Long maxSlipWins) {
		this.maxSlipWins = maxSlipWins;
	}
	/**
	 * 比较并设定单注最大中奖金额
	 */
	public void compareAndSetMaxSlipWins(Long maxSlipWins) {
		this.maxSlipWins = this.maxSlipWins>maxSlipWins ? this.maxSlipWins:maxSlipWins;
	}
	public Long getContinuousWinsIssue() {
		return continuousWinsIssue;
	}
	public void setContinuousWinsIssue(Long continuousWinsIssue) {
		this.continuousWinsIssue = continuousWinsIssue;
	}
	public void addContinuousWinsIssue(Long continuousWinsIssue) {
		this.continuousWinsIssue += continuousWinsIssue;
	}
	public Long getContinuousWinsTimes() {
		return continuousWinsTimes;
	}
	public void setContinuousWinsTimes(Long continuousWinsTimes) {
		this.continuousWinsTimes = continuousWinsTimes;
	}
	public void addContinuousWinsTimes(Long continuousWinsTimes) {
		this.continuousWinsTimes += continuousWinsTimes;
	}
	public Long getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Long isWarn) {
		this.isWarn = isWarn;
	}
	
	public void setWarn() {
		this.isWarn = 1L;
	}
	public void setIsNotWarn() {
		this.isWarn = 0L;
	}
}