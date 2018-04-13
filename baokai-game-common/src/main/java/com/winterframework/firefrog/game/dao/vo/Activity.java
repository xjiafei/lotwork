package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName Activity 
* @Description 活动表 
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class Activity extends BaseEntity{

// 数据库
//	  ID number(20) NOT NULL ,
//	  ACTIVITY_NAME varchar2(50) NULL ,
//	  START_TIME TIMESTAMP (3) NOT NULL ,
//	  END_TIME TIMESTAMP (3) NOT NULL ,
//	  STATUS NUMBER DEFAULT 0 NOT NULL , 
//	  ACTIVITY_TYPE NUMBER DEFAULT 0 NOT NULL , 
//	  GMT_CREATED TIMESTAMP (3) DEFAULT sysdate NOT NULL , 
//	  GMT_MODIFIED TIMESTAMP (3) DEFAULT sysdate NOT NULL ,
	
	
	private static final long serialVersionUID = -2145043309571048090L;
	
	
	private String activityName;
	private Date startTime;
	private Date endTime;
	private Long status;
	private Long activityType;
	
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
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
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getActivityType() {
		return activityType;
	}
	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}
	
	
}
