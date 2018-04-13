package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

public class LotteryIssueMonitorLogs implements Serializable {

	private static final long serialVersionUID = -8614519578488740379L;
	
	private String lotteryName;
	private Long lotteryId;
	private String webIssueCode;
	private Date createTime;
	private String warnDes;
	private String warnTypeName;
	private String disposeInfo;
	private String disposeUser;
	private String disposeMemo;
	private String doingMemo;
	private Integer	status;
	private String statusRout;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getWarnDes() {
		return warnDes;
	}
	public void setWarnDes(String warnDes) {
		this.warnDes = warnDes;
	}
	public String getWarnTypeName() {
		return warnTypeName;
	}
	public void setWarnTypeName(String warnTypeName) {
		this.warnTypeName = warnTypeName;
	}
	public String getDisposeInfo() {
		return disposeInfo;
	}
	public void setDisposeInfo(String disposeInfo) {
		this.disposeInfo = disposeInfo;
	}
	public String getDisposeUser() {
		return disposeUser;
	}
	public void setDisposeUser(String disposeUser) {
		this.disposeUser = disposeUser;
	}
	public String getDisposeMemo() {
		return disposeMemo;
	}
	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}
	public String getDoingMemo() {
		return doingMemo;
	}
	public void setDoingMemo(String doingMemo) {
		this.doingMemo = doingMemo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusRout() {
		return statusRout;
	}
	public void setStatusRout(String statusRout) {
		this.statusRout = statusRout;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
