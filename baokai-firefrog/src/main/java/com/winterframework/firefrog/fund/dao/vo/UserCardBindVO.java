/**   
* @Title: UserCardBindVO.java 
* @Package com.winterframework.firefrog.fund.dao.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-22 下午2:42:34 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: UserCardBindVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Alan
* @date 2013-7-22 下午2:42:34 
*  
*/
public class UserCardBindVO extends BaseEntity {

	private static final long serialVersionUID = 4850331161365697304L;

	private Long userId;
	private String account;
	private Integer userLvl;
	private Long lockId;
	private Date overTime;
	private String operator;
	private Date gmtCreated;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getUserLvl() {
		return userLvl;
	}
	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}
	public Long getLockId() {
		return lockId;
	}
	public void setLockId(Long lockId) {
		this.lockId = lockId;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	
}
