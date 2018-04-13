package com.winterframework.firefrog.advert.web.dto;

import java.util.Date;

/** 
* @ClassName: QueryRedEnvelopeRequest 
* @Description: 红包查询request
* @author david
* @date 
*  
*/
public class QueryRedEnvelopeRequest {
    private String account;//用户名
    
    private Date startTime;//查询时假如没有值清传递活动开始时间
    
    private Date endTime;//查询时假如没有值清传递活动结束时间
    
    private Integer status; //0未领取，1 已领取
    
    private  Integer channel;//渠道 -1 全部  0 pc 1 ios 2 andriod

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}
