/**   
* @Title: IUserNoticeTaskDao.java 
* @Package com.winterframework.firefrog.notice.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 上午9:49:54 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.dao;

import java.util.List;

import com.winterframework.firefrog.notice.entity.UserNoticeTask;

/** 
* @ClassName: IUserNoticeTaskDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 上午9:49:54 
*  
*/
public interface IUserNoticeTaskDao {
	
	public void update(UserNoticeTask task) throws Exception;
	public void deleteByUser(Long userId) throws Exception;

	public UserNoticeTask save(UserNoticeTask task) throws Exception;

	public List<UserNoticeTask> queryUserNoticeTaskByProperties(UserNoticeTask task) throws Exception;
}
