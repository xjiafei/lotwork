package com.winterframework.firefrog.shortlived.monkeyActivity.dto;

import java.util.Date;

public class LeaderboardRequest {

	private Long id;
	private Long rowNo;
	private Long userId;
	private String userAccount;
	private Long totAmount;
	private Date createTime;
	private Long lv;
	private Long luckyMoney;
	private Long kaiyun;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRowNo() {
		return rowNo;
	}
	public void setRowNo(Long rowNo) {
		this.rowNo = rowNo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Long getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(Long totAmount) {
		this.totAmount = totAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getLv() {
		return lv;
	}
	public void setLv(Long lv) {
		this.lv = lv;
	}
	public Long getLuckyMoney() {
		return luckyMoney;
	}
	public void setLuckyMoney(Long luckyMoney) {
		this.luckyMoney = luckyMoney;
	}
	public Long getKaiyun() {
		return kaiyun;
	}
	public void setKaiyun(Long kaiyun) {
		this.kaiyun = kaiyun;
	}
}
