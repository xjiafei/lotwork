package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class RechargeInitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3264629327472822213L;
	
	private List<RechargeInitDto> list;

	public List<RechargeInitDto> getList() {
		return list;
	}

	public void setList(List<RechargeInitDto> list) {
		this.list = list;
	}

}
