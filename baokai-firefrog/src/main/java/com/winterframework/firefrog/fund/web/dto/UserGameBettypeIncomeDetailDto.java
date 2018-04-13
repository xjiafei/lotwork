package com.winterframework.firefrog.fund.web.dto;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class UserGameBettypeIncomeDetailDto {


	private String methodCodeName;
	private Long bet;
	private Long ret;
	private Long trueBet;
	private Long win;
	private Long result;


	public String getMethodCodeName() {
		return methodCodeName;
	}
	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}
	public Long getBet() {
		return bet;
	}
	public void setBet(Long bet) {
		this.bet = bet;
	}
	public Long getRet() {
		return ret;
	}
	public void setRet(Long ret) {
		this.ret = ret;
	}
	public Long getTrueBet() {
		return trueBet;
	}
	public void setTrueBet(Long trueBet) {
		this.trueBet = trueBet;
	}
	public Long getWin() {
		return win;
	}
	public void setWin(Long win) {
		this.win = win;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}


	
}
