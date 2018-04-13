package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityLog 
* @Description 活动用户中奖日志表 
* @author  Ray
* @date 2015年08月19日 下午4:17:12 
*  
*/
public class ActivityLog extends BaseEntity{

// 数据库
//	"ID" NUMBER(20,0) NOT NULL ENABLE, 
//	"USER_ID" NUMBER(20,0) NOT NULL ENABLE, 
//	"ACTIVITY_ID" NUMBER(20,0) NOT NULL ENABLE, 
//	"PRIZE" NUMBER(20,0), 
//	"CREATE_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE, 
//	"AWARD_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE,  
//	"STATUS" NUMBER DEFAULT 0 NOT NULL ENABLE,
//  "MEMO" CLOB,
//	PRIMARY KEY ("ID")
	private static final long serialVersionUID = -2145043309571048092L;
	
	private Long userId;
	private Long activityId;
	private Float prize;
	private Date createTime;
	private Date awardTime;
	private Long status;
	private String memo;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAwardTime() {
		return awardTime;
	}
	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
