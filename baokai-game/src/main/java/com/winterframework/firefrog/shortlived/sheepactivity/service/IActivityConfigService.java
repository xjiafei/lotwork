package com.winterframework.firefrog.shortlived.sheepactivity.service;

import com.winterframework.firefrog.game.dao.vo.ActivityConfig;

/** 
* @ClassName IActivityAwardConfigService 
* @Description 活动配置 
* @author  hugh
* @date 2014年12月2日 下午3:33:43 
*  
*/
public interface IActivityConfigService {
	/** 
	* @Title: getActivityAwardConfigById 
	* @Description: 获取活动礼品设置相关信息
	* @param id
	* @return
	* @throws Exception
	*/
	ActivityConfig getById(Long id) throws Exception;
}
