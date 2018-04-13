package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CityListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6258674756546570339L;
	
	private Integer province;

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}
	
}
