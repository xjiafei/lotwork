package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.Date;

public class ActivitySheepUserRotaryRewardResponse {
	
	private Long id;
	private Long award;
	private Date date;
	private String desc;
	private Long lastTime;
	private boolean isHaveAward;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAward() {
		return award;
	}
	public void setAward(Long award) {
		this.award = award;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Long getLastTime() {
		return lastTime;
	}
	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}
	public boolean isHaveAward() {
		return isHaveAward;
	}
	public void setHaveAward(boolean isHaveAward) {
		this.isHaveAward = isHaveAward;
	}
}
