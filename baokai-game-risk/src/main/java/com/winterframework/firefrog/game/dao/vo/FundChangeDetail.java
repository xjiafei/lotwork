package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

public class FundChangeDetail {
	

	 public FundChangeDetail(String sn, String summary, Boolean addOrReduce, Long balance) {
		super();
		this.sn = sn;
		this.changeTime = new Date(System.currentTimeMillis());
		this.summary = summary;
		this.addOrReduce = addOrReduce;
		this.balance = balance;
	}
	private String sn;
	 private Date changeTime;
	 private String summary;
	 private Boolean addOrReduce;
	 private Long balance;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Boolean getAddOrReduce() {
		return addOrReduce;
	}
	public void setAddOrReduce(Boolean addOrReduce) {
		this.addOrReduce = addOrReduce;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	 

}
