package com.winterframework.firefrog.shortlived.sheepactivity.dto;

import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;

public class ActivitySheepConfigOperateRequest {
	
	private long isOpen;
	private long activityConfigId;
	
	private List<ActivitySheepOperateLog> logs ;

	public long getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(long isOpen) {
		this.isOpen = isOpen;
	}

	public List<ActivitySheepOperateLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ActivitySheepOperateLog> logs) {
		this.logs = logs;
	}

	public long getActivityConfigId() {
		return activityConfigId;
	}

	public void setActivityConfigId(long activityConfigId) {
		this.activityConfigId = activityConfigId;
	}
	
	
	
	
}
