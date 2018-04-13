/**   
* @Title: INoticeTaskDao.java 
* @Package com.winterframework.firefrog.notice.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午9:41:56 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.dao;

import java.util.List;

import com.winterframework.firefrog.notice.dao.vo.UserNoticeTaskVO;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;

/** 
* @ClassName: INoticeTaskDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午9:41:56 
*  
*/
public interface INoticeTaskDao {

	public void update(NoticeTask task) throws Exception;

	public List<NoticeTask> queryAllTask() throws Exception;

	public NoticeTask getNoticeTaskById(Long id) throws Exception;

	public NoticeTask getSendNoticeTask(Long id, Long userId) throws Exception;
	
	public UserNoticeTask getSendUserNoticeTask(Long id, Long userId) throws Exception;
}
