package com.winterframework.firefrog.shortlived.sheepactivity.service.util;

import java.util.Date;

public class Award {
	private boolean isHaveAward =true;
	private Long id;
	private Long award;
	private Date date;
	private String desc;
	private long lastGuessNum;//剩余次数
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
	
	public boolean isHaveAward() {
		return isHaveAward;
	}

	public void setHaveAward(boolean isHaveAward) {
		this.isHaveAward = isHaveAward;
	}
	public Award(boolean isHaveAward) {
		super();
		this.isHaveAward = isHaveAward;
	}


	public Award(Long id, Long award,String desc) {
		this.id = id;
		this.award = award;
		this.desc = desc;
	}

	public Award() {
	}

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

	public long getLastGuessNum() {
		return lastGuessNum;
	}

	public void setLastGuessNum(long lastGuessNum) {
		this.lastGuessNum = lastGuessNum;
	}

	
}
