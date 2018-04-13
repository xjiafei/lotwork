package com.winterframework.firefrog.shortlived.sheepactivity.service;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.Award;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IActivitySheepWheelSurfService {
	Page<ActivitySheepWheelSurf> queryPage(PageRequest<ActivitySheepWheelSurf> pr) ;
	void update(ActivitySheepWheelSurf activity) throws Exception;
	void updateEntityByType(ActivitySheepWheelSurf activity) throws Exception;
	Award getAward(Long userId, Long activityId, Long channel) throws Exception;
	ActivitySheepWheelSurf getUserRotary(Long userId);
	void initUserRotary(Long userId, String userName,Long channel);
	void initUserRotary(Long userId, Long times, Long amount,Long channel);
	void addUserRotaryLastNum(Long userId, Long times, Long amount);
}
