package com.winterframework.firefrog.user.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class LevelRecycle extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	public enum RecycleStatus {
		INIT(0), RUNING(1),FINISH(2),ERROR(3);

		private RecycleStatus(int status) {
			this.status = status;
		}

		@SuppressWarnings("unused")
		private int status;

		public int getIntegerValue() {
			return status;
		}
	}
	
	public static final String SUCCESS = "SUCCESS";	
	public static final String ERROR = "ERROR";
	
	//重置预设密码
	public static final String DEFAULT_PASSWD = "123qweasd";
	
	//清除奖金组
	public static final int DEFAULT_BETTYPE = 0;

	private String account;

	private Long userId;

	private String topAgent;

	private Long availBal;

	private String recycleReason;
	
	private String operator;
	
	private Integer taskStatus;
	
	private String recycleStatus;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Date activityDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTopAgent() {
		return topAgent;
	}

	public void setTopAgent(String topAgent) {
		this.topAgent = topAgent;
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

	public String getRecycleStatus() {
		return recycleStatus;
	}

	public void setRecycleStatus(String recycleStatus) {
		this.recycleStatus = recycleStatus;
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

}
