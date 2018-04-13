/**   
* @Title: IUserNoticeTaskService.java 
* @Package com.winterframework.firefrog.notice.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 上午11:08:14 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.service;

import java.util.List;

import com.winterframework.firefrog.notice.entity.UserNoticeTask;

/** 
* @ClassName: IUserNoticeTaskService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 上午11:08:14 
*  
*/
public interface IUserNoticeTaskService {
	public void saveOrUpdateUserNoticeTask(UserNoticeTask task) throws Exception;
	public void deleteByUser(Long userId) throws Exception;

	public List<UserNoticeTask> queryUserNoticeTaskByUser(Long userId) throws Exception;
}
