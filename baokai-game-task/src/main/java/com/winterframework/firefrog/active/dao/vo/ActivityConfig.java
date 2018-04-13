package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityAwardConfig 
* @Description 活动奖品配置表
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class ActivityConfig extends BaseEntity{


//	 	"ID" NUMBER(20,0) NOT NULL ENABLE, 
//		"BEGIN_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE, 
//		"END_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE, 
//		"MAX_PRIZE" FLOAT(126) DEFAULT 0 NOT NULL ENABLE, 
//		"MIN_PRIZE" FLOAT(126) DEFAULT 0 NOT NULL ENABLE, 
//		"MEMO" VARCHAR2(256 BYTE), 
//		PRIMARY KEY ("ID")
	 
	
	
	private static final long serialVersionUID = -2145043309571048091L;
	
	private Long id;
	private Date beginTime;
	private Date endTime;
	private Long maxPrize;
	private Long minPrize;
	private String memo;
	private String odds;
	private Long activityId;
	private String type;
	private String rule;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getMaxPrize() {
		return maxPrize;
	}
	public void setMaxPrize(Long maxPrize) {
		this.maxPrize = maxPrize;
	}
	public Long getMinPrize() {
		return minPrize;
	}
	public void setMinPrize(Long minPrize) {
		this.minPrize = minPrize;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	
	
	
}
