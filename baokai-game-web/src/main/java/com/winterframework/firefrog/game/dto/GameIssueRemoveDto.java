package com.winterframework.firefrog.game.dto;

public class GameIssueRemoveDto {

	
	private Long LotteryId;
	private String awardTime = null;
	private String gameTime = null;
	private String orderTime = null;
	
	
	public Long getLotteryId() {
		return LotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		LotteryId = lotteryId;
	}
	public String getAwardTime() {
		return awardTime;
	}
	public void setAwardTime(String awardTime) {
		this.awardTime = awardTime;
	}
	public String getGameTime() {
		return gameTime;
	}
	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
}
