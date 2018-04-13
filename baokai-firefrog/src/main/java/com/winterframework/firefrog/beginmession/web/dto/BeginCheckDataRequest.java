package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

public class BeginCheckDataRequest implements Serializable{

	private Long pageNo;
	
	private Long status;
	
	private Long timeStart;
	
	private Long timeEnd;

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}

	public Long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Long timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
