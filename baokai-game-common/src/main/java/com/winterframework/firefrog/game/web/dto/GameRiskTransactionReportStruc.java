package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
/**
 * 
* @ClassName: GameRiskTransactionReportStruc 
* @Description:游戏交易报表响应
* @author hugh
* @date 2014-4-24 上午11:01:28 
*
 */
public class GameRiskTransactionReportStruc implements Serializable {

	private static final long serialVersionUID = 1426370762708226020L;

	private String tid;
	private String userName;
	private String tradeDate;
	private String gameType;
	private String planCode;
	private String orderCode;
	private String reson;
	private Long amonut;
    private String amountD;
	private String lotteryName;
	private String status;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}
	public Long getAmonut() {
		return amonut;
	}
	public void setAmonut(Long amonut) {
		this.amonut = amonut;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmountD() {
		return amountD;
	}
	public void setAmountD(String amountD) {
		this.amountD = amountD;
	}

//    public Double getAmountD() {
//    	this.amountD= Double.valueOf(amonut)/10000.00;
//        return this.amountD;
//    }
//	public void setAmountD(Double amountD) {
//		this.amountD = amountD;
//	}
}
