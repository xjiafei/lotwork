package com.winterframework.firefrog.user.entity;

import java.util.Date;
import java.util.Map;
/**
 * key="firefrog_index_lastdata_lotteryid"
 * @author heny
 *
 */
public class IndexLottery {

	private Long lotteryId;
	private String lottery;
	private String currentIssue;
	private Date saleEndTime;
	//格式"04,30;08,21;"
	private String omityTrend;
	//奖池大小，双色球
	private Long pondAmount=6000000L;
	//map account,money
    private Map<String,String> wins;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getCurrentIssue() {
		return currentIssue;
	}
	public void setCurrentIssue(String currentIssue) {
		this.currentIssue = currentIssue;
	}
	public Date getSaleEndTime() {
		return saleEndTime;
	}
	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}
	public String getOmityTrend() {
		return omityTrend;
	}
	public void setOmityTrend(String omityTrend) {
		this.omityTrend = omityTrend;
	}
	public Map<String, String> getWins() {
		return wins;
	}
	public void setWins(Map<String, String> wins) {
		this.wins = wins;
	}
	public Long getPondAmount() {
		return pondAmount;
	}
	public void setPondAmount(Long pondAmount) {
		this.pondAmount = pondAmount;
	}
	/**
	 * @return the lottery
	 */
	public String getLottery() {
		return lottery;
	}
	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

    
}
