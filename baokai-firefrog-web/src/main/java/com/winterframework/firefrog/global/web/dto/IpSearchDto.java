package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

public class IpSearchDto implements Serializable{
	
	private static final long serialVersionUID = 472697812036127926L;

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
