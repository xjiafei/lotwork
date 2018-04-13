package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewLogWebResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1810694077279217159L;

	
	private List<BeginNewLog> logs;
	
	private Long type;


	public List<BeginNewLog> getLogs() {
		return logs;
	}


	public void setLogs(List<BeginNewLog> logs) {
		this.logs = logs;
	}


	public Long getType() {
		return type;
	}


	public void setType(Long type) {
		this.type = type;
	}
	
}
