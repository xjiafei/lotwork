package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.Date;

import com.winterframework.firefrog.common.util.DateUtils;

public class ActivitySheepDetailCount {
	private Date countDate;
	
	private Long getNum;
	
	private Long useNum;
	
	private Long winNum;
	
	private Long failNum;
	
	private Long win8Num;
	
	private Long award;
	
	private String level;
	
	private String levels;
	
	private Long channel;
	
	private String countDateStr;
	
	private String channelStr;

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public Long getGetNum() {
		if(getNum == null){
			return 0l;
		}
		return getNum;
	}

	public void setGetNum(Long getNum) {
		this.getNum = getNum;
	}

	public Long getUseNum() {
		if(useNum==null){
			return 0l;
		}
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
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
	
	public String getCountDateStr() {
		return DateUtils.format(countDate);
	}

	public void setCountDateStr(String countDateStr) {
		this.countDateStr = countDateStr;
	}

	public String getChannelStr() {
		if(channel==null){
			return "";
		}
		if(channel==1){
			return "ios2.1";
		} 
		if(channel==2){
			return "android2.1";
		} 
		if(channel==3){
			return "web3.0";
		} 
		if(channel==4){
			return "web4.0";
		}
		return "";
	}

	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}
}
