package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GameOrderWinsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677848218915948845L;
	private Long lotteryId;
	private String lottery;
	private String currentIssue;
	private Date saleEndTime;
	private List<GameOrderWinsDetailDTO> wins;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getLottery() {
		return lottery;
	}
	public void setLottery(String lottery) {
		this.lottery = lottery;
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
	public List<GameOrderWinsDetailDTO> getWins() {
		return wins;
	}
	public void setWins(List<GameOrderWinsDetailDTO> wins) {
		this.wins = wins;
	}
	
	
	
}
