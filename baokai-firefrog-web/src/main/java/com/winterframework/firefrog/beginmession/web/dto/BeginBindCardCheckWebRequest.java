package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginBindCardCheckWebRequest implements Serializable{

	private static final long serialVersionUID = -7713059471094718804L;
	
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
