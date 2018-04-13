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
public class RiskOrderDownloadStruc implements Serializable {

	private static final long serialVersionUID = 7484118503897326726L;


	private String lotteryName;
	private String webIssueCode;
	private String account;
	private Double countWins;
	private Double issueWinsRatio;
	private Long orderwarnContinuousWins;
	private Long continuousWinsTimes;

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getCountWins() {
		return countWins;
	}

	public void setCountWins(Double countWins) {
		this.countWins = countWins;
	}

	public Double getIssueWinsRatio() {
		return issueWinsRatio;
	}

	public void setIssueWinsRatio(Double issueWinsRatio) {
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

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

}
