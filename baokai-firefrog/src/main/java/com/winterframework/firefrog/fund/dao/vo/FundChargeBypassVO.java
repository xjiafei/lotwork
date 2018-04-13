package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class FundChargeBypassVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748690734543516637L;

	private Long id;

	private String userAccount;
	
	private Date createTime;
	
	private String memo;
	
	private String deleteFlag;
	
	private Long chargeChannel;
	
	private Long chargeWaySet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getChargeChannel() {
		return chargeChannel;
	}

	public void setChargeChannel(Long chargeChannel) {
		this.chargeChannel = chargeChannel;
	}

	public Long getChargeWaySet() {
		return chargeWaySet;
	}

	public void setChargeWaySet(Long chargeWaySet) {
		this.chargeWaySet = chargeWaySet;
	}

	
}
