package com.winterframework.firefrog.beginmession.entity;

import java.io.Serializable;
import java.util.Date;

public class BeginCheckData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3106663281304655352L;

	private Long pageNo;
	
	private Long status;
	
	private Date timeStart;
	
	private Date timeEnd;

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

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
