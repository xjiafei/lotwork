package com.winterframework.firefrog.help.excel.entity;

/**
 * @author 22477
 *
 */
public class Oper {
	
	private String bizDate;
	private String activeUserCount;
	private String profit;
	private String betAmt;
	private String chargeAmt;
	private String withdrawAmt;
	
	
	
	public Oper() {
		super();
	}
	public Oper(String bizDate, String activeUserCount, String profit, String betAmt, String chargeAmt,
			String withdrawAmt) {
		super();
		this.bizDate = bizDate;
		this.activeUserCount = activeUserCount;
		this.profit = profit;
		this.betAmt = betAmt;
		this.chargeAmt = chargeAmt;
		this.withdrawAmt = withdrawAmt;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public String getActiveUserCount() {
		return activeUserCount;
	}
	public void setActiveUserCount(String activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getBetAmt() {
		return betAmt;
	}
	public void setBetAmt(String betAmt) {
		this.betAmt = betAmt;
	}
	public String getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(String chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getWithdrawAmt() {
		return withdrawAmt;
	}
	public void setWithdrawAmt(String withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	
	

	
	
}
