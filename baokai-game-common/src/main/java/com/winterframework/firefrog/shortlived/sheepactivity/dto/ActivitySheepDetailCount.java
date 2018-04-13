package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.Date;

public class ActivitySheepDetailCount {
	private Date countDate;
	
	private Long getNum = 0L;
	
	private Long useNum = 0L;
	
	private Long winNum = 0L;
	
	private Long failNum = 0L;
	
	private Long win8Num = 0L;
	
	private Long award = 0L;
	
	private String level;
	private String levels;
	
	private Long channel;

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public Long getGetNum() {
		return getNum;
	}

	public void setGetNum(Long getNum) {
		this.getNum = getNum;
	}

	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
	}

	public Long getWinNum() {
		return winNum;
	}

	public void setWinNum(Long winNum) {
		this.winNum = winNum;
	}

	public Long getFailNum() {
		return failNum;
	}

	public void setFailNum(Long failNum) {
		this.failNum = failNum;
	}

	public Long getWin8Num() {
		return win8Num;
	}

	public void setWin8Num(Long win8Num) {
		this.win8Num = win8Num;
	}

	public String getLevel() {
		return levels;
	}

	public void setLevel(String level) {
		this.level = this.levels;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.level = levels;
		this.levels = levels;
	}
	
	
}
