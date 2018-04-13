package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;



public class SlmmcRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261958850000898252L;
	private Float amount;//s	投注金额
	private Long saleTime;
	private Long userIp;
	private List<Projects> balls;
	private Integer channelId;
	private String channelVersion;
		
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelVersion() {
		return channelVersion;
	}
	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}
	public Long getUserIp() {
		return userIp;
	}
	public void setUserIp(Long userIp) {
		this.userIp = userIp;
	}
	public Long getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}
	
	
	
	

}
