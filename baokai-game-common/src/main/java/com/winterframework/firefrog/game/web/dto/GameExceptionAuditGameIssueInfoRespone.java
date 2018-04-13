package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameExceptionAuditGameIssueInfoRespone 
* @Description: 审核异常奖期需要显示的奖期信息 
* @author david 
* @date 2014-2-25 下午4:56:36 
*  
*/
public class GameExceptionAuditGameIssueInfoRespone implements Serializable {

	private static final long serialVersionUID = -5460275329436605623L;

	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private String saleDate;
	private String openDrawTime;
	private String confirmDrawTime;
	
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

	
}
