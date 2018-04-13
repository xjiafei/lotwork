package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;

public class NoticeListResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7007887491374987568L;
	private List<NoticeListDto> list;

	public List<NoticeListDto> getList() {
		return list;
	}

	public void setList(List<NoticeListDto> list) {
		this.list = list;
	}
}
