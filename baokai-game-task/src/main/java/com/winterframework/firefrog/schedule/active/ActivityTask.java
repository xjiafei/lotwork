package com.winterframework.firefrog.schedule.active;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.active.service.IActivitySeptemberService;

public abstract class ActivityTask {
	
	protected static final Logger log = LoggerFactory.getLogger(ActivityTask.class);
	
	protected IActivitySeptemberService activitySeptemberService; 
	
	public abstract void execute() throws Exception;

	public IActivitySeptemberService getActivitySeptemberService() {
		return activitySeptemberService;
	}

	public void setActivitySeptemberService(IActivitySeptemberService activitySeptemberService) {
		this.activitySeptemberService = activitySeptemberService;
	}
	
	
}
