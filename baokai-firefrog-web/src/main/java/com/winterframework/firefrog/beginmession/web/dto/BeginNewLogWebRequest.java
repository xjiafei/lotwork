package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

public class BeginNewLogWebRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6737401802994077816L;

	private Long type;
	
	private Long pageNo;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
