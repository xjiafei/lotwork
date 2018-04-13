package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class HistoryInfoResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4967666406484305845L;
	private List<HistoryInfoResponseDto> list;

	public List<HistoryInfoResponseDto> getList() {
		return list;
	}

	public void setList(List<HistoryInfoResponseDto> list) {
		this.list = list;
	}

}
