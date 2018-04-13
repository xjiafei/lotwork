package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class OpenLinkListResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9044068423507901387L;
	private List<OpenLinkListDto> list;

	public List<OpenLinkListDto> getList() {
		return list;
	}

	public void setList(List<OpenLinkListDto> list) {
		this.list = list;
	}
}
