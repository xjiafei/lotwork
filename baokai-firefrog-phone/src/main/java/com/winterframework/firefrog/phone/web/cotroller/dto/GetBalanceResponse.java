package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class GetBalanceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1204957538105931021L;
	private Double balance;

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double d) {
		this.balance = d;
	}
}
