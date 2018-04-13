package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameExceptionAuditStrucDTO implements Serializable {

	private static final long serialVersionUID = -5460275329436605623L;

	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private String saleDate;
	//private String salePeriod;
	private String openDrawTime;
	private String numberRecord;
	//private Integer periodStatus;
	private String confirmDrawTime;
	//private String warnDescStr;
	private Integer status;
	private String operator;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
//	public String getSalePeriod() {
//		return salePeriod;
//	}
//	public void setSalePeriod(String salePeriod) {
//		this.salePeriod = salePeriod;
//	}
	public String getOpenDrawTime() {
		return openDrawTime;
	}
	public void setOpenDrawTime(String openDrawTime) {
		this.openDrawTime = openDrawTime;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
//	public Integer getPeriodStatus() {
//		return periodStatus;
//	}
//	public void setPeriodStatus(Integer periodStatus) {
//		this.periodStatus = periodStatus;
//	}
	public String getConfirmDrawTime() {
		return confirmDrawTime;
	}
	public void setConfirmDrawTime(String confirmDrawTime) {
		this.confirmDrawTime = confirmDrawTime;
	}
//	public String getWarnDescStr() {
//		return warnDescStr;
//	}
//	public void setWarnDescStr(String warnDescStr) {
//		this.warnDescStr = warnDescStr;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
