package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: WinsReport 
* @Description: 运营盈亏实体Bean 
* @author Denny 
* @date 2013-10-16 下午3:26:21 
*  
*/
public class WinsReport implements Serializable {

	private static final long serialVersionUID = -3502915473322845357L;

	private Long lotteryid;
	private String lotteryName;
	private Long lotteryTypeCode;
	private Long lotterySeriesCode;
	private Long gameGroupCode;
	private String gameGroupName;
	private Long gameSetCode;
	private String gameSetName;
	private Long betMethodCode;
	private String betMethodName;
	private Long issueCode;
	private String webIssueCode;
	private Long totalSales;
	private Long totalPoints;
	private Long totalWins;
	private Long totalProfit;
	private Long totalCancel;
	private Date reportDate;

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

	public Long getLotteryTypeCode() {
		return lotteryTypeCode;
	}

	public void setLotteryTypeCode(Long lotteryTypeCode) {
		this.lotteryTypeCode = lotteryTypeCode;
	}

	public Long getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public void setLotterySeriesCode(Long lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	public Long getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Long gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Long getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Long gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Long getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Long betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	public Long getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Long getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}

	public Long getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Long totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Long getTotalCancel() {
		return totalCancel;
	}

	public void setTotalCancel(Long totalCancel) {
		this.totalCancel = totalCancel;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getGameGroupName() {
		return gameGroupName;
	}

	public void setGameGroupName(String gameGroupName) {
		this.gameGroupName = gameGroupName;
	}

	public String getGameSetName() {
		return gameSetName;
	}

	public void setGameSetName(String gameSetName) {
		this.gameSetName = gameSetName;
	}

	public String getBetMethodName() {
		return betMethodName;
	}

	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
	}
}
