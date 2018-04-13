package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**
 * @ClassName: FreezeUserLogStruc
 * @Description: 冻结账户列表数据结构
 * @author 你的名字
 * @date 2013-5-7 上午11:24:32
 * 
 */
public class QueryUnFreezeUserLogStruc implements Serializable {

	private static final long serialVersionUID = -7790870506669359730L;

	private int uid;
	private String userName;
	private Integer userGroup;
	private long restBal;
	private long freeDate;
	private long frozenDate;
	private String reason;
	private String operator;
	private long vipLvl;
	private String memo;
	private String freezeAccount;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
		this.freezeAccount = freezeAccount;
	}

	/**
	 * @return the vipLvl
	 */
	public long getVipLvl() {
		return vipLvl;
	}

	/**
	 * @param vipLvl
	 *            the vipLvl to set
	 */
	public void setVipLvl(long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

	public long getRestBal() {
		return restBal;
	}

	public void setRestBal(long restBal) {
		this.restBal = restBal;
	}

	public long getFreeDate() {
		return freeDate;
	}

	public void setFreeDate(long freeDate) {
		this.freeDate = freeDate;
	}

	public long getFrozenDate() {
		return frozenDate;
	}

	public void setFrozenDate(long frozenDate) {
		this.frozenDate = frozenDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
