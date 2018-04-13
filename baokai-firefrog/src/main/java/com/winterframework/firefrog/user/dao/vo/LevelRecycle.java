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

		private int status;

		public int getIntegerValue() {
			return status;
		}
	}
	
	public enum PtResultStatus {
		SUCCESS(0), FAIL(-1),NOUSER(2);

		private PtResultStatus(int status) {
			this.status = status;
		}

		private int status;

		public int getIntegerValue() {
			return status;
		}
	}
	
	public enum RecycleIndex {
		cleanAwardGroup(1), cleanSafeCenter(2),cleanPersonalinfo(3),cleanBindcard(4)
		,cleanOrderHistory(5),cleanUserMessage(6),resetPtPassword(7),resetLoginPassword(8);

		private RecycleIndex(int status) {
			this.status = status;
		}

		private int status;

		public int getIntegerValue() {
			return status;
		}
	}
	
	public static final String SUCCESS = "SUCCESS";
	
	public static final String ERROR = "ERROR";	
	
	public static final String DEFAULT_PASSWD = "123qweasd"; //重置预设密码	
	
	public static final int DEFAULT_BETTYPE = 0; //清除奖金组
	
	public static final String DEFAULT_RECYCLE_STATUS = "00000000";
	
	public static final char RECYCLE_UNIT_SUCCESS = '1';
	
	public static final String RECYCLE_ALL_SUCCESS = "11111111";
	
	//PT重置密码失敗status
	public static final int PT_CHANGPWD_FAIL = 0;

	private Long id;
	
	private String account;

	private Long userId;

	private String topAgent;

	private Long availBal;
	
	private Long availPtBal;

	private String recycleReason;
	
	private String operator;
	
	private Integer taskStatus;
	
	private String recycleStatus;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Date activityDate;
	
	private Long vipLvl;
	
	private Date lastLoginDate;
	
	private Long lastLoginIp;
	
	private String lastLoginAddress;
	
	private Boolean changePwd;

	
	
	/**
	 * @return the vipLvl
	 */
	public Long getVipLvl() {
		return vipLvl;
	}

	/**
	 * @param vipLvl the vipLvl to set
	 */
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAvailPtBal() {
		return availPtBal;
	}

	public void setAvailPtBal(Long availPtBal) {
		this.availPtBal = availPtBal;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	public Boolean getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(Boolean changePwd) {
		this.changePwd = changePwd;
	}

}
