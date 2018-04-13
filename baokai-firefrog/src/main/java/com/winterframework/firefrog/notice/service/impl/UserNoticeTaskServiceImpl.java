/**   
* @Title: UserNoticeTaskServiceImpl.java 
* @Package com.winterframework.firefrog.notice.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 上午11:16:17 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.notice.service.IUserNoticeTaskService;

/** 
* @ClassName: UserNoticeTaskServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 上午11:16:17 
*  
*/
@Service("userNoticeTaskServiceImpl")
public class UserNoticeTaskServiceImpl implements IUserNoticeTaskService {

	@Resource(name = "userNoticeTaskDaoImpl")
	private IUserNoticeTaskDao userNoticeTaskDaoImpl;

	/**
	* Title: saveOrUpdateUserNoticeTask
	* Description:
	* @param task
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.IUserNoticeTaskService#saveOrUpdateUserNoticeTask(com.winterframework.firefrog.notice.entity.UserNoticeTask) 
	*/
	@Override
	public void saveOrUpdateUserNoticeTask(UserNoticeTask task) throws Exception {
		task.setTask(task.getId().intValue()+"");
		task.setId(null);
		userNoticeTaskDaoImpl.save(task);
	
	}
	public void deleteByUser(Long userId) throws Exception {
		userNoticeTaskDaoImpl.deleteByUser(userId);
	}
	
	

	/**
	* Title: queryUserNoticeTaskByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.service.IUserNoticeTaskService#queryUserNoticeTaskByUser(java.lang.Long) 
	*/
	@Override
	public List<UserNoticeTask> queryUserNoticeTaskByUser(Long userId) throws Exception {
		UserNoticeTask task = new UserNoticeTask();
		task.setUserId(userId);
		return userNoticeTaskDaoImpl.queryUserNoticeTaskByProperties(task);
	}

}
