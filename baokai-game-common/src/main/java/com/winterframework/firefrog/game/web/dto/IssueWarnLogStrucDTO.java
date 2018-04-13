package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class IssueWarnLogStrucDTO implements Serializable {

	private static final long serialVersionUID = -1759004547427121741L;

	private String lotteryName;
	private String webIssueCode;
	private String createTime;
	private String warnDes;
	private String warnTypeName;
	private String disposeInfo;
	private String disposeUser;
	private String disposeMemo;
	private String doingMemo;
	private String	status;//处理状态 1 待处理 2 已处理
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
