package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
/**
 * 彩种奖期基本结构
* @ClassName: LotteryIssueStruc 
* @Description: 彩种奖期基本结构 CSI028
* @author Richard
* @date 2013-10-12 上午9:31:31 
*
 */
public class LotteryIssueStruc implements Serializable {
	
	private static final long serialVersionUID = -7316209330051891485L;
	
	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private String saleDate;
	private String salePeriod;
	private Long openDrawTime;
	private String numberRecord;
	private Integer periodStatus;
	private Integer pauseStatus;
	private Long confirmDrawTime;
	private Long recivceDrawTime;
	private String warnDescStr;
	
	public Integer getPauseStatus() {
		return pauseStatus;
	}
	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}
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
	public String getSalePeriod() {
		return salePeriod;
	}
	public void setSalePeriod(String salePeriod) {
		this.salePeriod = salePeriod;
	}
	public Long getOpenDrawTime() {
		return openDrawTime;
	}
	public void setOpenDrawTime(Long openDrawTime) {
		this.openDrawTime = openDrawTime;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	
	/**
	 * 
	* @Title: getPeriodStatus 
	* @Description: 0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
	* @return
	 */
	public Integer getPeriodStatus() {
		return periodStatus;
	}
	/**
	 * 
	* @Title: setPeriodStatus 
	* @Description: 0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
	* @param periodStatus
	 */
	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}
	public Long getConfirmDrawTime() {
		return confirmDrawTime;
	}
	public void setConfirmDrawTime(Long confirmDrawTime) {
		this.confirmDrawTime = confirmDrawTime;
	}
	
	/**
	 * 
	* @Title: getWarnDescStr 
	* @Description: 传输异常情况描述ID，如1,2,3,4
	* @return
	 */
	public String getWarnDescStr() {
		return warnDescStr;
	}
	
	/**
	 * 
	* @Title: setWarnDescStr 
	* @Description: 传输异常情况描述ID，如1,2,3,4
	* @param warnDescStr
	 */
	public void setWarnDescStr(String warnDescStr) {
		this.warnDescStr = warnDescStr;
	}
	public Long getRecivceDrawTime() {
		return recivceDrawTime;
	}
	public void setRecivceDrawTime(Long recivceDrawTime) {
		this.recivceDrawTime = recivceDrawTime;
	}
	
}
