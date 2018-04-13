package com.winterframework.firefrog.schedule.active.sept;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.schedule.active.ActivityTask;

public class Activity20160903DailyTask extends ActivityTask{

	protected static final Logger log = LoggerFactory.getLogger(Activity20160903DailyTask.class);
	
//	protected IActivitySeptemberService activitySeptemberService; 
	
	public void execute() throws Exception{
		Calendar yesterday = Calendar.getInstance();
		activitySeptemberService.daySumUp(yesterday);
	}
}
