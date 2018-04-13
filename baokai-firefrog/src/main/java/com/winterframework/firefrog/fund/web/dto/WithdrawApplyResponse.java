package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;

public class WithdrawApplyResponse implements Serializable {

	private static final long serialVersionUID = -2307258520608903498L;
	private List<FundWithdrawOrder> orders;
	public List<FundWithdrawOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<FundWithdrawOrder> orders) {
		this.orders = orders;
	}

}
