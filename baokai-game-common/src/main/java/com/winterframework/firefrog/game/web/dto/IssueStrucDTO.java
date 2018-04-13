package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class IssueStrucDTO implements Serializable {

	private static final long serialVersionUID = -4551449142434313929L;

	private String webIssueCode;
	private String saleStartTime;
	private String saleEndTime;
	private String openAwardTime;
	private boolean display;
	private Long issueCode;
	private Integer periodStatus;
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private Long id;
	
	
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public String getSaleStartTime() {
		return saleStartTime;
	}
	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}
	public String getSaleEndTime() {
		return saleEndTime;
	}
	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}
	public String getOpenAwardTime() {
		return openAwardTime;
	}
	public void setOpenAwardTime(String openAwardTime) {
		this.openAwardTime = openAwardTime;
	}
	public Integer getPeriodStatus() {
		return periodStatus;
	}
	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}
}
