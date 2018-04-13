package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Ray.Wang
 *
 */
public class BeginCancelListDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806389057014818782L;

	private List<Long> statusList;
	
	private Date timeStart;
	
	private Date timeEnd;

	public List<Long> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Long> statusList) {
		this.statusList = statusList;
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
