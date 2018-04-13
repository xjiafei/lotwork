/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author hugh
 * @version 1.0
 * @since 1.0
 */

public class FundTransferView extends BaseEntity {

	private static final long serialVersionUID = 3310103376767128748L;
	//alias
	public static final String TABLE_ALIAS = "转账只要符合条件，永远成功，所以不需要状态";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_TRANSFER_AMT = "转账金额";
	public static final String ALIAS_GMT_CREATED = "转账时间";
	public static final String ALIAS_RCV_USER_ID = "接受者用户id";
	public static final String ALIAS_IS_UPWARD = "是否对上级 1）对上级  -1）对下级";
	public static final String ALIAS_USER_CHAIN = "用户链";
	public static final String ALIAS_SN = "流水号";
	public static final String ALIAS_RCV_ACCOUNT = "接受者账号";
	public static final String ALIAS_ACCOUNT = "用户";
	public static final String ALIAS_USER_CHAIN_TRUE = "代理";
	public static final String ALIAS_VIP_LVL = "用户级别";
	//date formats


	//columns START
	private Long userId;
	private Long transferAmt;
	private Long rcvUserId;
	private Long isUpward;
	private String userChain;
	private String sn;
	private String rcvAccount;
	private String userAccount;

	private String account;
	private Long vipLevel;
	private String userChainTure;
	
	//columns END


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(Long vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getUserChainTure() {
		return userChainTure;
	}

	public void setUserChainTure(String userChainTure) {
		this.userChainTure = userChainTure;
	}

	public FundTransferView() {
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public FundTransferView(Long id) {
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setTransferAmt(Long value) {
		this.transferAmt = value;
	}

	public Long getTransferAmt() {
		return this.transferAmt;
	}

	public void setRcvUserId(Long value) {
		this.rcvUserId = value;
	}

	public Long getRcvUserId() {
		return this.rcvUserId;
	}

	public void setIsUpward(Long value) {
		this.isUpward = value;
	}

	public Long getIsUpward() {
		return this.isUpward;
	}

	public void setUserChain(String value) {
		this.userChain = value;
	}

	public String getUserChain() {
		return this.userChain;
	}

	public void setSn(String value) {
		this.sn = value;
	}

	public String getSn() {
		return this.sn;
	}

	public void setRcvAccount(String value) {
		this.rcvAccount = value;
	}

	public String getRcvAccount() {
		return this.rcvAccount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("UserId", getUserId())
				.append("GmtCreated", getGmtCreated()).append("TransferAmt", getTransferAmt())
				.append("RcvUserId", getRcvUserId()).append("IsUpward", getIsUpward())
				.append("UserChain", getUserChain()).append("Sn", getSn()).append("RcvAccount", getRcvAccount())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getUserId()).append(getGmtCreated())
				.append(getTransferAmt()).append(getRcvUserId()).append(getIsUpward()).append(getUserChain())
				.append(getSn()).append(getRcvAccount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FundTransferView == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		FundTransferView other = (FundTransferView) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getUserId(), other.getUserId())

		.append(getGmtCreated(), other.getGmtCreated())

		.append(getTransferAmt(), other.getTransferAmt())

		.append(getRcvUserId(), other.getRcvUserId())

		.append(getIsUpward(), other.getIsUpward())

		.append(getUserChain(), other.getUserChain())

		.append(getSn(), other.getSn())

		.append(getRcvAccount(), other.getRcvAccount())

		.isEquals();
	}
}
