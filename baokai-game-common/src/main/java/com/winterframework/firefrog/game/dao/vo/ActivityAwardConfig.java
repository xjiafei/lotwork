package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityAwardConfig 
* @Description 活动奖品配置表
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class ActivityAwardConfig extends BaseEntity{

// 数据库
//	  ID number(20) NOT NULL ,
//	  ACTIVITY_ID number(20) NOT NULL ,
//	  AWARD NUMBER DEFAULT 0 NOT NULL , 
//	  AWARD_NAME varchar2(50)  NOT NULL ,
//		RATIO NUMBER DEFAULT 0 NOT NULL , 
//	  MULTIPLE NUMBER DEFAULT 0 NOT NULL , 
//	  ALL_NUMBER NUMBER DEFAULT 0 NOT NULL , 
//	  LAST_NUMBER NUMBER DEFAULT 0 NOT NULL ,
//	  WIN_NUMBER NUMBER DEFAULT 0 NOT NULL ,
//	  GMT_CREATED TIMESTAMP (3) DEFAULT sysdate NOT NULL , 
//		GMT_MODIFIED TIMESTAMP (3) DEFAULT sysdate NOT NULL ,
//		RECHARGE_LIMIT NUMBER NULL ,
//	  ORDER_NUM NUMBER DEFAULT 0 NOT NULL ,
//	  PRIMARY KEY (ID)
	
	
	private static final long serialVersionUID = -2145043309571048090L;
	
	private Long activityId;
	private Long award;
	private String awardName;
	private Long ratio;
	private Long multiple;
	private Long allNumber;
	private Long lastNumber;
	private Long winNumber;
	private Long rechargeLimit;
	private Long orderNum;
	
	
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getAward() {
		return award;
	}
	public void setAward(Long award) {
		this.award = award;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Long getRatio() {
		return ratio;
	}
	public void setRatio(Long ratio) {
		this.ratio = ratio;
	}
	public Long getMultiple() {
		return multiple;
	}
	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}
	public Long getAllNumber() {
		return allNumber;
	}
	public void setAllNumber(Long allNumber) {
		this.allNumber = allNumber;
	}
	public Long getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(Long lastNumber) {
		this.lastNumber = lastNumber;
	}
	public Long getWinNumber() {
		return winNumber;
	}
	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}
	public Long getRechargeLimit() {
		return rechargeLimit;
	}
	public void setRechargeLimit(Long rechargeLimit) {
		this.rechargeLimit = rechargeLimit;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
	
	
}
