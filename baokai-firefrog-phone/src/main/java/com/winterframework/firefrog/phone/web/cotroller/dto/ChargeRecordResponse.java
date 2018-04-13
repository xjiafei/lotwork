package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class ChargeRecordResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261605812573842562L;

	private List<ChargeRecordDto> list;

	public List<ChargeRecordDto> getList() {
		return list;
	}

	public void setList(List<ChargeRecordDto> list) {
		this.list = list;
	}
	
}
