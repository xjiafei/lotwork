package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class QueryLevelRecycleHistoryResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private Long userId; 
	
	/**
	 * 用户名
	 */
	private String account;
	
	/**
	 * 所属总代
	 */
	private String topAgent;
	
	/**
	 * 申请时间
	 */	
	private Date createDate;
	
	/**
	 * 更新时间
	 */
	private Date updateDate;
	
	/**
	 * 生效时间
	 */
	private Date activityDate;
	
	/**
	 * 可用余额
	 */
	private Long availBal;	
	
	/**
	 * 回收原因
	 */
	private String recycleReason;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 状态<br>
	 * 全部步骤成功:11111111
	 * 步骤失败该位数显示:0
	 * 
	 */
	private String recycleStatus;
	
	/**
	 * Task状态<br>
	 * 0:未执行
	 * 1:执行中
	 * 2:执行成功
	 * 3:执行失败
	 */
	private Integer taskStatus;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getAvailBal() {
		return availBal;
	}

	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}

	public String getRecycleReason() {
		return recycleReason;
	}

	public void setRecycleReason(String recycleReason) {
		this.recycleReason = recycleReason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRecycleStatus() {
		return recycleStatus;
	}

	public void setRecycleStatus(String recycleStatus) {
		this.recycleStatus = recycleStatus;
	}
	
	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getTopAgent() {
		return topAgent;
	}

	public void setTopAgent(String topAgent) {
		this.topAgent = topAgent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
