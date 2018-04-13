package com.winterframework.firefrog.shortlived.sheepactivity.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.ActivityAwardConfig;

/** 
* @ClassName IActivityAwardConfigService 
* @Description 活动配置 
* @author  hugh
* @date 2014年12月2日 下午3:33:43 
*  
*/
public interface IActivityAwardConfigService {
	/** 
	* @Title: getActivityAwardConfigById 
	* @Description: 获取活动礼品设置相关信息
	* @param id
	* @return
	* @throws Exception
	*/
	List<ActivityAwardConfig> getActivityAwardConfigByActivityId(Long id) throws Exception;
	
	void update(ActivityAwardConfig config) throws Exception;
	ActivityAwardConfig getById(Long id) throws Exception;
}
