package com.winterframework.firefrog.fund.web.controller.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;

public class DatePeriod extends WithCharge{
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date startDate;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date endDate;
	private Long onlineChargeStart;
	private Long onlineChargeEnd;
	private Long manualAddCoinStart;
	private Long manualAddCoinEnd;
	private Long chargeSumBegin;
	private Long chargeSumEnd;
	private Long withdrawSumBegin;
	private Long withdrawSumEnd;
	private Long surplusBegin;
	private Long surplusEnd;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Long getOnlineChargeStart() {
		return onlineChargeStart;
	}
	public void setOnlineChargeStart(Long onlineChargeStart) {
		this.onlineChargeStart = onlineChargeStart;
	}
	public Long getOnlineChargeEnd() {
		return onlineChargeEnd;
	}
	public void setOnlineChargeEnd(Long onlineChargeEnd) {
		this.onlineChargeEnd = onlineChargeEnd;
	}
	public Long getManualAddCoinStart() {
		return manualAddCoinStart;
	}
	public void setManualAddCoinStart(Long manualAddCoinStart) {
		this.manualAddCoinStart = manualAddCoinStart;
	}
	public Long getManualAddCoinEnd() {
		return manualAddCoinEnd;
	}
	public void setManualAddCoinEnd(Long manualAddCoinEnd) {
		this.manualAddCoinEnd = manualAddCoinEnd;
	}
	public Long getChargeSumBegin() {
		return chargeSumBegin;
	}
	public void setChargeSumBegin(Long chargeSumBegin) {
		this.chargeSumBegin = chargeSumBegin;
	}
	public Long getChargeSumEnd() {
		return chargeSumEnd;
	}
	public void setChargeSumEnd(Long chargeSumEnd) {
		this.chargeSumEnd = chargeSumEnd;
	}
	public Long getWithdrawSumBegin() {
		return withdrawSumBegin;
	}
	public void setWithdrawSumBegin(Long withdrawSumBegin) {
		this.withdrawSumBegin = withdrawSumBegin;
	}
	public Long getWithdrawSumEnd() {
		return withdrawSumEnd;
	}
	public void setWithdrawSumEnd(Long withdrawSumEnd) {
		this.withdrawSumEnd = withdrawSumEnd;
	}
	public Long getSurplusBegin() {
		return surplusBegin;
	}
	public void setSurplusBegin(Long surplusBegin) {
		this.surplusBegin = surplusBegin;
	}
	public Long getSurplusEnd() {
		return surplusEnd;
	}
	public void setSurplusEnd(Long surplusEnd) {
		this.surplusEnd = surplusEnd;
	}
	

}
