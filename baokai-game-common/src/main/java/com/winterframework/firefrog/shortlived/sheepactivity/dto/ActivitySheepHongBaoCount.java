package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.Date;

public class ActivitySheepHongBaoCount {
	
	private Date countDate;
	
	private Long sighNum = 0L;
	
	private Long reachNum= 0L;
	
	private Long getNum= 0L;
	
	private Long channel;

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public Long getSighNum() {
		return sighNum;
	}

	public void setSighNum(Long sighNum) {
		this.sighNum = sighNum;
	}

	public Long getReachNum() {
		return reachNum;
	}

	public void setReachNum(Long reachNum) {
		this.reachNum = reachNum;
	}

	public Long getGetNum() {
		return getNum;
	}

	public void setGetNum(Long getNum) {
		this.getNum = getNum;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}
	
}
