package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityLog 
* @Description 活动用户中奖日志表 
* @author  Ray
* @date 2015年08月19日 下午4:17:12 
*  
*/
public class ActivityDoubleboxLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7117302634833712819L;
	private Long userId;
	private String userAccount;
	private Long activityId;
	private Date createTime;
	private Float multiple;
	private Long multiplePrize;
	private Long sales;
	
	public Long getSales() {
		return sales;
	}
	public void setSales(Long sales) {
		this.sales = sales;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Float getMultiple() {
		return multiple;
	}
	public void setMultiple(Float multiple) {
		this.multiple = multiple;
	}
	public Long getMultiplePrize() {
		return multiplePrize;
	}
	public void setMultiplePrize(Long multiplePrize) {
		this.multiplePrize = multiplePrize;
	}
}
