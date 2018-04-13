package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ChargeRecordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757821931848595547L;
	
	private Integer chargeType;

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	
	

}
