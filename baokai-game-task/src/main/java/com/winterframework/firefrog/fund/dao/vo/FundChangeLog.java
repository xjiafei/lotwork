/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundChangeLog extends BaseEntity {

	private static final long serialVersionUID = 4433537427422073345L;
	//alias
	public static final String TABLE_ALIAS = "FundChangeLog";
	public static final String ALIAS_USER_ID = "用户";
	public static final String ALIAS_BEFOR_BAL = "老的余额";
	public static final String ALIAS_BEFORE_DAMT = "老的不可提现余额";
	public static final String ALIAS_CT_BAL = "当前余额";
	public static final String ALIAS_CT_DAMT = "当前不可提现余额";
	public static final String ALIAS_REASON = "原因";
	public static final String ALIAS_OPERATOR = "操作人 0系统";
	public static final String ALIAS_FUND_ID = "funds表外键";
	public static final String ALIAS_T_CODE = "流水号";

	//date formats

	//columns START
	private Long userId;
	private Long beforBal;
	private Long beforeDamt;
	private Long ctBal;
	private Long ctDamt;
	private String reason;
	private String reansonStr;
	private String account;
	private Long operator;
	private Long fundId;
	private Long frozeAmt;
	private Long changeAmt;
	private String sn;
	private String fundSn;
	private String summary;
	private Long isVisiblebyFrontUser;
	private String exCode;
	private String planCode;
	private String note;
	private String status;
	private Long ctAvailBal;
	private Long beforeAvailBal;
	private Long oldFreezeAmt;
	private Long currentFreezeAmt;
	
	
	public Long getCtAvailBal() {
		return ctAvailBal;
	}

	public void setCtAvailBal(Long ctAvailBal) {
		this.ctAvailBal = ctAvailBal;
	}

	public Long getBeforeAvailBal() {
		return beforeAvailBal;
	}

	public void setBeforeAvailBal(Long beforeAvailBal) {
		this.beforeAvailBal = beforeAvailBal;
	}

	public String getPlanCode() {
		return planCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSummary() {
		if (summary == null && this.reason != null) {
			String[] values = reason.split(",");
			if (values.length > 0)
				return "null".equals(values[1])?null:values[1];
		}
		return summary;
	}

	public Long getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(Long changeAmt) {
		this.changeAmt = changeAmt;
	}

	public Long getFrozeAmt() {
		return frozeAmt;
	}

	public void setFrozeAmt(Long frozeAmt) {
		this.frozeAmt = frozeAmt;
	}

	public String getReansonStr() {
		return reansonStr;
	}

	public void setReansonStr(String reansonStr) {
		this.reansonStr = reansonStr;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	private Long isAclUser;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBeforBal() {
		return beforBal;
	}

	public void setBeforBal(Long beforBal) {
		this.beforBal = beforBal;
	}

	public Long getBeforeDamt() {
		return beforeDamt;
	}

	public void setBeforeDamt(Long beforeDamt) {
		this.beforeDamt = beforeDamt;
	}

	public Long getCtBal() {
		return ctBal;
	}

	public void setCtBal(Long ctBal) {
		this.ctBal = ctBal;
	}

	public Long getCtDamt() {
		return ctDamt;
	}

	public void setCtDamt(Long ctDamt) {
		this.ctDamt = ctDamt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getFundSn() {
		return fundSn;
	}

	public void setFundSn(String fundSn) {
		this.fundSn = fundSn;
	}

	public Long getIsVisiblebyFrontUser() {
		return isVisiblebyFrontUser;
	}

	public void setIsVisiblebyFrontUser(Long isVisiblebyFrontUser) {
		this.isVisiblebyFrontUser = isVisiblebyFrontUser;
	}

	public Long getIsAclUser() {
		return isAclUser;
	}

	public void setIsAclUser(Long isAclUser) {
		this.isAclUser = isAclUser;
	}

	public Long getOldFreezeAmt() {
		return oldFreezeAmt;
	}

	public void setOldFreezeAmt(Long oldFreezeAmt) {
		this.oldFreezeAmt = oldFreezeAmt;
	}

	public Long getCurrentFreezeAmt() {
		return currentFreezeAmt;
	}

	public void setCurrentFreezeAmt(Long currentFreezeAmt) {
		this.currentFreezeAmt = currentFreezeAmt;
	}
}
