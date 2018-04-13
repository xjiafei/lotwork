package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

public class FundChargeBypassRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3427156899867212113L;

	private Long pageNo;
	
	private Long chargeChannel;
	
	private Long deleteId;
	
	private String account;
	
	private String memo;
	
	private Long chargeWaySet;

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getChargeChannel() {
		return chargeChannel;
	}

	public void setChargeChannel(Long chargeChannel) {
		this.chargeChannel = chargeChannel;
	}

	public Long getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getChargeWaySet() {
		return chargeWaySet;
	}

	public void setChargeWaySet(Long chargeWaySet) {
		this.chargeWaySet = chargeWaySet;
	}

}
