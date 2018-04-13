package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class BankReportRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7567291228152322187L;
	
	private String ordertype;//	筛选格式

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

}
