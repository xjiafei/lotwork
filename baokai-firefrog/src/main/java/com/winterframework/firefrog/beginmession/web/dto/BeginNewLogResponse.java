package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewLogResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1810694077279217159L;

	
	private List<BeginNewLog> logs;

	public List<BeginNewLog> getLogs() {
		return logs;
	}


	public void setLogs(List<BeginNewLog> logs) {
		this.logs = logs;
	}
	
}
