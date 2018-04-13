/**   
* @Title: INoticeTaskService.java 
* @Package com.winterframework.firefrog.notice.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午11:36:35 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.service;

import java.util.List;

import com.winterframework.firefrog.notice.entity.MQMsg;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.notice.web.dto.NoticeStruc;

/** 
* @ClassName: INoticeTaskService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午11:36:35 
*  
*/
public interface INoticeTaskService {

	public void update(NoticeTask task) throws Exception;

	public List<NoticeTask> queryAllTask() throws Exception;

	public NoticeTask getNoticeTaskById(Long id) throws Exception;

	public NoticeTask getSendNoticeTask(Long id, Long userId) throws Exception;
	
	public UserNoticeTask getSendUserNoticeTask(Long taskid, Long userId) throws Exception ;
	
	public void sendNoticeTask(NoticeTask notice, MQMsg taskPram) throws Exception;
	
	public List<NoticeStruc> getNoticeForUser(Long userId) throws Exception;
}
