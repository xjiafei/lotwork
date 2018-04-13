package com.winterframework.firefrog.help.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class OperVO extends BaseEntity {
	
	private static final long serialVersionUID = -5360848579620975445L;
	private String bizDate;
	private String activeUserCount;
	private String profit;
	private String betAmt;
	private String winAmt;
	private String retAmt;
	private String chargeAmt;
	private String withdrawAmt;
	private String createTime;
	private String updateTime;
	
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
	public String getWinAmt() {
		return winAmt;
	}
	public void setWinAmt(String winAmt) {
		this.winAmt = winAmt;
	}
	public String getRetAmt() {
		return retAmt;
	}
	public void setRetAmt(String retAmt) {
		this.retAmt = retAmt;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
