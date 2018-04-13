package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Ray.Wang
 *
 */
public class BeginCancelListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806389057014818782L;

	private List<Long> statusList;
	
	private Long timeStart;
	
	private Long timeEnd;

	public List<Long> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Long> statusList) {
		this.statusList = statusList;
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
