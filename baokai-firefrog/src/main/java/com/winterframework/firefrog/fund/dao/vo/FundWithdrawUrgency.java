package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 緊急提示
 * @ClassName FundWithdrawUrgency.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月22日
 *
 */
public class FundWithdrawUrgency extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6705875618413749080L;

	private String urgencyContext;

	private Date createTime;

	private String createUser;

	private String createTimeText ;
	
	private Date updateTime;

	private String updateUser;
	
	private String cancelFlag;
	
	
	
	public String getUrgencyContext() {
		return urgencyContext;
	}

	public void setUrgencyContext(String urgencyContext) {
		this.urgencyContext = urgencyContext;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTimeText() {
		return createTimeText;
	}

	public void setCreateTimeText(String createTimeText) {
		this.createTimeText = createTimeText;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

}
