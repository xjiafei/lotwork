package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityLogRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long userId;
	private Long activityId;
	private Float prize;
	private String memo;
	private Date beginTime;
	private Date endTime;


	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Float getPrize() {
		return prize;
	}

	public void setPrize(Float prize) {
		this.prize = prize;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	

}
