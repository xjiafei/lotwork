package com.winterframework.firefrog.fund.entity;

import java.util.Date;

/**
 * 
* @ClassName: MCFundWithdraw 
* @Description: 提现申请Mownecum信息
* @author Richard
* @date 2013-6-28 下午4:40:17 
*
 */
public class FundWithdrawMCOrder {

	private Date mcRemitTime;
	private String mcSN;
	private Date mcNoticeTime;
	private Long mcFee;
	private String mcMemo;

	public FundWithdrawMCOrder() {

	}

	public Date getMcRemitTime() {
		return mcRemitTime;
	}

	public void setMcRemitTime(Date mcRemitTime) {
		this.mcRemitTime = mcRemitTime;
	}

	public String getMcSN() {
		return mcSN;
	}

	public void setMcSN(String mcSN) {
		this.mcSN = mcSN;
	}

	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
	}

	public Long getMcFee() {
		return mcFee;
	}

	public void setMcFee(Long mcFee) {
		this.mcFee = mcFee;
	}

	public String getMcMemo() {
		return mcMemo;
	}

	public void setMcMemo(String mcMemo) {
		this.mcMemo = mcMemo;
	}

}
