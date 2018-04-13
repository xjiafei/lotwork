package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

public class FundRechargeAppealResponse {

	private String appealSn;
	
	private String account;

	private Integer userlvl;
	
	private Long appealAmt;

	private Date appealTime;
	
	private String status;

	private String title;
	
	private String reviewer;

	private Date reviewStartTime;
	
	private Date reviewEndTime;

	private String memo;
	
	private Integer isvip;
	
	private String tenpayAccount;
	
	private String tenpayName;
	
	private String reviewMemo;
	
	private Integer vipLvl;


	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getReviewMemo() {
		return reviewMemo;
	}

	public void setReviewMemo(String reviewMemo) {
		this.reviewMemo = reviewMemo;
	}

	public String getAppealSn() {
		return appealSn;
	}

	public void setAppealSn(String appealSn) {
		this.appealSn = appealSn;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUserlvl() {
		return userlvl;
	}

	public void setUserlvl(Integer userlvl) {
		this.userlvl = userlvl;
	}

	public Long getAppealAmt() {
		return appealAmt;
	}

	public void setAppealAmt(Long appealAmt) {
		this.appealAmt = appealAmt;
	}

	public Date getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewStartTime() {
		return reviewStartTime;
	}

	public void setReviewStartTime(Date reviewStartTime) {
		this.reviewStartTime = reviewStartTime;
	}

	public Date getReviewEndTime() {
		return reviewEndTime;
	}

	public void setReviewEndTime(Date reviewEndTime) {
		this.reviewEndTime = reviewEndTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getIsvip() {
		return isvip;
	}

	public void setIsvip(Integer isvip) {
		this.isvip = isvip;
	}

	public String getTenpayAccount() {
		return tenpayAccount;
	}

	public void setTenpayAccount(String tenpayAccount) {
		this.tenpayAccount = tenpayAccount;
	}

	public String getTenpayName() {
		return tenpayName;
	}

	public void setTenpayName(String tenpayName) {
		this.tenpayName = tenpayName;
	}
}
