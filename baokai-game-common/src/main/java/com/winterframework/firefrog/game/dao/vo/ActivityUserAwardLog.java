package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityUserAwardLog 
* @Description 活动用户中奖日志表 
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class ActivityUserAwardLog extends BaseEntity{

// 数据库
//	  ID number(20) NOT NULL ,
//	  USER_ID number(20) NOT NULL ,
//	  ACTIVITY_ID number(20) NOT NULL ,
//	  ACTIVITY_AWARD_CONFIG_ID number(20) NULL ,
//	  AWARD number(20) NULL ,
//	  REMARK varchar2(50) NULL ,
//	  GMT_CREATED TIMESTAMP (3) DEFAULT sysdate NOT NULL , 
	
	
	private static final long serialVersionUID = -2145043309571048090L;
	
	private Long userId;
	private Long activityId;
	private Long activityAwardConfigId;
	private Long award;
	private String remark;
	private String dailyBetAwardTime;
	private String userName;
	private Integer rewardChannel;
	private String awardName;
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
	public Long getActivityAwardConfigId() {
		return activityAwardConfigId;
	}
	public void setActivityAwardConfigId(Long activityAwardConfigId) {
		this.activityAwardConfigId = activityAwardConfigId;
	}
	public Long getAward() {
		return award;
	}
	public void setAward(Long award) {
		this.award = award;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDailyBetAwardTime() {
		return dailyBetAwardTime;
	}
	public void setDailyBetAwardTime(String dailyBetAwardTime) {
		this.dailyBetAwardTime = dailyBetAwardTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Integer getRewardChannel() {
		return rewardChannel;
	}
	public void setRewardChannel(Integer rewardChannel) {
		this.rewardChannel = rewardChannel;
	}
}
