package com.winterframework.firefrog.fund.entity;

import java.util.Date;


public class MowCallbackInfo {

	public enum Status {
		//取现
		failed(5L), sucessful(4L), incomplete(6L),
		//异常
		excep_failed(6L), excep_sucessful(5L), excep_incomplete(7L);
		private  Status(Long v){
			this.value=v;
		}
		private Long value;
		public Long getValue() {
			return value;
		}
		
	}

	private String mowOrderNum;

	private String companyOrderNum;

	private Status status;

	private String detail;

	private String key;

	private String errorMsg;

	private Long amount;
	private Date noticeTime;
	private Date responseTime;
	private Long mowTransactionCharge;
	private Date operatingTime;
	
	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public Long getMowTransactionCharge() {
		return mowTransactionCharge;
	}

	public void setMowTransactionCharge(Long mowTransactionCharge) {
		this.mowTransactionCharge = mowTransactionCharge;
	}

	public String getMowOrderNum() {
		return mowOrderNum;
	}

	public void setMowOrderNum(String mowOrderNum) {
		this.mowOrderNum = mowOrderNum;
	}

	public String getCompanyOrderNum() {
		return companyOrderNum;
	}

	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getAmount() {
		return amount==null?0L:amount;
	}
	
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

}
