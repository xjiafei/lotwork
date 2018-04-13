package com.winterframework.firefrog.help.dao.vo;

import java.io.Serializable;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class OperReportSumVO extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5188411066235934005L;

	private String activeUserAVG;

	private String profitSum;

	private String betAmtSum;

	private String chargeAmtSum;

	private String withdrawAmtSum;

	
	
	public OperReportSumVO() {
		super();
	}

	public OperReportSumVO(String activeUserAVG, String profitSum, String betAmtSum, String chargeAmtSum,
			String withdrawAmtSum) {
		super();
		this.activeUserAVG = activeUserAVG;
		this.profitSum = profitSum;
		this.betAmtSum = betAmtSum;
		this.chargeAmtSum = chargeAmtSum;
		this.withdrawAmtSum = withdrawAmtSum;
	}

	public String getactiveUserAVG() {
		return activeUserAVG;
	}

	public void setactiveUserAVG(String activeUserAVG) {
		this.activeUserAVG = activeUserAVG;
	}

	public String getProfitSum() {
		return profitSum;
	}

	public void setProfitSum(String profitSum) {
		this.profitSum = profitSum;
	}

	public String getBetAmtSum() {
		return betAmtSum;
	}

	public void setBetAmtSum(String betAmtSum) {
		this.betAmtSum = betAmtSum;
	}

	public String getChargeAmtSum() {
		return chargeAmtSum;
	}

	public void setChargeAmtSum(String chargeAmtSum) {
		this.chargeAmtSum = chargeAmtSum;
	}

	public String getWithdrawAmtSum() {
		return withdrawAmtSum;
	}

	public void setWithdrawAmtSum(String withdrawAmtSum) {
		this.withdrawAmtSum = withdrawAmtSum;
	}

	
	
	
	
}
