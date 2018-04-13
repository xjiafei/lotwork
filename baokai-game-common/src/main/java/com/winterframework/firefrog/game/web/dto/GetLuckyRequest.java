package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;


/** 
* @ClassName GetLuckyRequest 
* @Description 用户抽奖请求
* @author  david
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class GetLuckyRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long userId;
	
	private Date activityStartTime;
	
	private Date activityEndTime;
	
	private Integer channel;

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	
}
