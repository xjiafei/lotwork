package com.winterframework.firefrog.fund.entity;

import java.util.Date;

public class FundChargeMCOrder {

	private Long fee;

	private Date expireTime;

	private String errorMsg;

	private String channel;

	private String area;

	private String uuid;

	private String sn;
	private Long payBankId;

	private Long bankFee;
	private Long mcBankFee;
	private Long amount;
	private Long bankId;

	private String tempSn;
	private Date noticeTime;	
	private FundChargeOrder order;

	//	public FundChargeMCOrder() {
	//		order = new FundChargeOrder();
	//	}

	public Long getFee() {
		return fee;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getMcBankFee() {
		return mcBankFee;
	}

	public void setMcBankFee(Long mcBankFee) {
		this.mcBankFee = mcBankFee;
	}

	public Long getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(Long payBankId) {
		this.payBankId = payBankId;
	}

	public Long getAmount() {
		return amount==null?(0L):amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getBankFee() {
		return bankFee==null?0L:bankFee;
	}

	public void setBankFee(Long bankFee) {
		this.bankFee = bankFee;
	}

	public String getTempSn() {
		return tempSn;
	}

	public void setTempSn(String tempSn) {
		this.tempSn = tempSn;
	}

	public FundChargeOrder getOrder() {
		return order;
	}

	public void setOrder(FundChargeOrder order) {
		this.order = order;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

}