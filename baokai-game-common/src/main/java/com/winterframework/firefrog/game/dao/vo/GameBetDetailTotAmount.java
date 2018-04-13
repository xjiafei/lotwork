package com.winterframework.firefrog.game.dao.vo;

public class GameBetDetailTotAmount {

	/** 單一玩法所有投注金額*/
	private Long totAmount;
	/** 玩法組+玩法+投注號碼*/
	private String betDetail;
	
	public Long getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(Long totAmount) {
		this.totAmount = totAmount;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	
}
