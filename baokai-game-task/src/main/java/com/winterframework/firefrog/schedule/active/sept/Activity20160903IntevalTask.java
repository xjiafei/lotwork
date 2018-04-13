package com.winterframework.firefrog.schedule.active.sept;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.schedule.active.ActivityTask;

public class Activity20160903IntevalTask extends ActivityTask{

	protected static final Logger log = LoggerFactory.getLogger(Activity20160903IntevalTask.class);
	
	public void execute() throws Exception{
		activitySeptemberService.intevalSumUp(new Date(), Boolean.FALSE);
	}
}
