package com.winterframework.firefrog.active.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityLog;

public interface IOctActivityService {

	public Long queryUserPeriodBets(Long userId);
	
	public String getScale(Long amount,Boolean isVip);
	
	public ActivityLog isGetPrize(Long userId);
	
	public Long getLeftAmount(Long amount);

	public String getMultiple(Long amount);

	public ActivityLog saveActivityLog(Long param,Long activityId , String creatParam, Date beginTime,Date endTime);

	public void saveFundChaneLog(Long userId, Long amount);

	public ActivityLog isSignUp(Long userId, Long activityId,Date beginTime,Date endTime);

	public List<String> checkMultiple();

	public String getPrize(Long charge, Long amount, Boolean isVip);
}
