/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.user.entity.User;

/**
 * @author david
 * @version 1.0
 * @since 1.0
 */

public class FundTransferOrder extends FundOrder {

	public FundTransferOrder(EnumItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -1907629041449067865L;
	private Long transferAmt;
	private User receiveUser;
	private Long isUpward;
	private String userChain;
	private String sn;
	private Date retTime;	
	private Long amountBal;
	private String note;


	public Date getRetTime() {
		return retTime;
	}

	public void setRetTime(Date retTime) {
		this.retTime = retTime;
	}

	@Override
	public String getSn() {
		return sn;
	}
	
	@Override
	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getTransferAmt() {
		return transferAmt;
	}

	public void setTransferAmt(Long transferAmt) {
		this.transferAmt = transferAmt;
	}

	public Long getIsUpward() {
		return isUpward;
	}

	public void setIsUpward(Long isUpward) {
		this.isUpward = isUpward;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	public Long getAmountBal() {
		return amountBal;
	}

	public void setAmountBal(Long amountBal) {
		this.amountBal = amountBal;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
