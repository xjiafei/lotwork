package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName UserSystemUpdateLog 
* @Description 用户升级日志 
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class UserSystemUpdateLog extends BaseEntity{

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
	private Long isUpdate = 0L;
	private Long isRecharge = 0L;
	private Long isBet= 0L;
	private Date updateTime;
	private Date rechargeTime;
	private Date betTime;
	public Long getUserId() {
		return userId;
	}
	public Long getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Long isUpdate) {
		this.isUpdate = isUpdate;
	}
	public Long getIsRecharge() {
		return isRecharge;
	}
	public void setIsRecharge(Long isRecharge) {
		this.isRecharge = isRecharge;
	}
	public Long getIsBet() {
		return isBet;
	}
	public void setIsBet(Long isBet) {
		this.isBet = isBet;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public Date getBetTime() {
		return betTime;
	}
	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
		
}
