package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: GameWarnIssueList 
* @Description: 用于彩种奖期列表映射 
* @author Richard
* @date 2013-10-14 上午10:21:40 
*
 */
public class GameWarnIssueList implements Serializable {

	private static final long serialVersionUID = 3444928360951251712L;

	 private Long lotteryid;
     private String lotteryName;
     private String webIssueCode;
     private Long issueCode;
     private Date saleStartTime;
     private Date saleEndTime;
     private Date openDrawTime;
     private Date recivceDrawTime;
     private Date factionDrawTime;
     private Integer status;
     private Integer periodStatus;
     private String numberRecord;
     private Integer pauseStatus;
     private String warnParas;
     private String des;
     private Integer warnType;
     private String disposeMemo;
     private String statusRout;
     private Long noticeStatus;
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
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Date getSaleStartTime() {
		return saleStartTime;
	}
	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}
	public Date getSaleEndTime() {
		return saleEndTime;
	}
	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}
	public Date getOpenDrawTime() {
		return openDrawTime;
	}
	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}
	public Date getFactionDrawTime() {
		return factionDrawTime;
	}
	public void setFactionDrawTime(Date factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public String getWarnParas() {
		return warnParas;
	}
	public void setWarnParas(String warnParas) {
		this.warnParas = warnParas;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Integer getWarnType() {
		return warnType;
	}
	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}
	public String getDisposeMemo() {
		return disposeMemo;
	}
	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}
	public Integer getPeriodStatus() {
		return periodStatus;
	}
	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}
	public String getStatusRout() {
		return statusRout;
	}
	public void setStatusRout(String statusRout) {
		this.statusRout = statusRout;
	}
	public Date getRecivceDrawTime() {
		return recivceDrawTime;
	}
	public void setRecivceDrawTime(Date recivceDrawTime) {
		this.recivceDrawTime = recivceDrawTime;
	}
	public Long getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(Long noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public Integer getPauseStatus() {
		return pauseStatus;
	}
	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}
     
}
