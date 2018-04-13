package com.winterframework.firefrog.game.entity;

public class GameBetDetailTotAmountEntity {

	/** 單一玩法所有投注金額*/
	private Long totAmount;
	/** 玩法*/
	private String methodCodeTitle;

	private String betTypeCode;
	
	private String betDetail;
	
	public Long getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(Long totAmount) {
		this.totAmount = totAmount;
	}
	public String getMethodCodeTitle() {
		return methodCodeTitle;
	}
	public void setMethodCodeTitle(String methodCodeTitle) {
		this.methodCodeTitle = methodCodeTitle;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	
}
