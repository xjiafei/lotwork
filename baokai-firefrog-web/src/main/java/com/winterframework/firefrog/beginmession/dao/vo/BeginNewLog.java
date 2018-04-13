package com.winterframework.firefrog.beginmession.dao.vo;

import java.util.Date;


/**
 * 
 * @author Ami.Tsai
 *
 */
public class BeginNewLog {

	private Long logType;

	private Date logTime;

	private String title;

	private String beforeUpdate;

	private String afterUpdate;

	private String logUser;
	
	private String updateTimeStr;

	public Long getLogType() {
		return logType;
	}

	public void setLogType(Long logType) {
		this.logType = logType;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBeforeUpdate() {
		return beforeUpdate;
	}

	public void setBeforeUpdate(String beforeUpdate) {
		this.beforeUpdate = beforeUpdate;
	}

	public String getAfterUpdate() {
		return afterUpdate;
	}

	public void setAfterUpdate(String afterUpdate) {
		this.afterUpdate = afterUpdate;
	}

	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

}
