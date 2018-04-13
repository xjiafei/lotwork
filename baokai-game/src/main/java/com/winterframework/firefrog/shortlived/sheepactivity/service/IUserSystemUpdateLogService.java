package com.winterframework.firefrog.shortlived.sheepactivity.service;

import com.winterframework.firefrog.game.dao.vo.UserSystemUpdateLog;

/** 
* @ClassName IUserSystemUpdateLogService 
* @Description 用户平台升级日志 
* @author  hugh
* @date 2014年12月2日 下午3:34:14 
*  
*/
public interface IUserSystemUpdateLogService {
	
	/** 
	* @Title: getUserUpdateSystemByUserId 
	* @Description: 根据用户id获取用户升级情况
	* @param userId
	* @return
	* @throws Exception
	*/
	UserSystemUpdateLog getUserUpdateSystemByUserId(Long userId) throws Exception;
	
	/** 
	* @Title: userUpdateSystem 
	* @Description: 更新用户升级情况
	* @param userId
	* @param type
	* @return
	* @throws Exception
	*/
	String userUpdateSystem(Long userId, Integer type) throws Exception;
}
