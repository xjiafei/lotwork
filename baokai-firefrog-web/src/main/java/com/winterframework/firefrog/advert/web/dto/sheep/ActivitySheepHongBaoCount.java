package com.winterframework.firefrog.advert.web.dto.sheep;

import java.util.Date;

import com.winterframework.firefrog.common.util.DateUtils;

public class ActivitySheepHongBaoCount {
	
	private Date countDate;
	
	private Long sighNum;
	
	private Long reachNum;
	
	private Long getNum;
	
	private Long channel;
	
	private String countDateStr;
	
	private String channelStr;

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

	public String getCountDateStr() {
		return DateUtils.format(countDate);
	}

	public void setCountDateStr(String countDateStr) {
		this.countDateStr = countDateStr;
	}

	public String getChannelStr() {
		if(channel== null){
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
		return null;
	}

	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}
}
