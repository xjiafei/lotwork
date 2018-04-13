package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryLotteryResultRequest implements Serializable{

	private static final long serialVersionUID = 7565663294657611576L;
    private String account;
    
    private Date startTime;
    
    private Date endTime;
    
    private Long awardId;
    
    private Integer channel;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}
