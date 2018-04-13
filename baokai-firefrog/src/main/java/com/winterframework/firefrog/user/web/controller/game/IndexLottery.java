package com.winterframework.firefrog.user.web.controller.game;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class IndexLottery {

	private Long lotteryId;
	private String currentIssue;
	private Date saleEndTime;
	//格式"04,30;08,21;"
	private String omityTrend;
	//奖池大小，双色球
	private Long pondAmount=6000000L;
	//map account,money
    private List<Map<String,String>> wins;
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
	public List<Map<String, String>> getWins() {
		return wins;
	}
	public void setWins(List<Map<String, String>> wins) {
		this.wins = wins;
	}
	public Long getPondAmount() {
		return pondAmount;
	}
	public void setPondAmount(Long pondAmount) {
		this.pondAmount = pondAmount;
	}
    
}
