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
public class ActivityRedenvelopeLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6480534191548522259L;
	private Long userId;
	private String userAccount;
	private Long activityId;
	private Date createTime;
	private Integer vipLvl;
	private Long redEnvelope;
	
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
	public Integer getVipLvl() {
		return vipLvl;
	}
	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}
	public Long getRedEnvelope() {
		return redEnvelope;
	}
	public void setRedEnvelope(Long redEnvelope) {
		this.redEnvelope = redEnvelope;
	}
}
