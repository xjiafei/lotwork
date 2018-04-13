/**   
* @Title: NoticeTaskDaoImpl.java 
* @Package com.winterframework.firefrog.notice.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午10:02:14 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.notice.dao.INoticeTaskDao;
import com.winterframework.firefrog.notice.dao.vo.NoticeTaskVO;
import com.winterframework.firefrog.notice.dao.vo.UserNoticeTaskVO;
import com.winterframework.firefrog.notice.dao.vo.VOConverter;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: NoticeTaskDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午10:02:14 
*  
*/
@Repository("noticeTaskDaoImpl")
public class NoticeTaskDaoImpl extends BaseIbatis3Dao<NoticeTaskVO> implements INoticeTaskDao {

	/**
	* Title: update
	* Description:
	* @param task
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeTaskDao#update(com.winterframework.firefrog.notice.entity.NoticeTask) 
	*/
	@Override
	public void update(NoticeTask task) throws Exception {
		this.update(VOConverter.transNoticeTask2Vo(task));
	}

	/**
	* Title: queryAllTask
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeTaskDao#queryAllTask() 
	*/
	@Override
	public List<NoticeTask> queryAllTask() throws Exception {
		List<NoticeTaskVO> vos = this.getAll();
		List<NoticeTask> tasks = new ArrayList<NoticeTask>();
		for (NoticeTaskVO vo : vos) {
			tasks.add(VOConverter.transVo2NoticeTask(vo));
		}
		return tasks;
	}

	/**
	* Title: getNoticeTaskById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeTaskDao#getNoticeTaskById(java.lang.Long) 
	*/
	@Override
	public NoticeTask getNoticeTaskById(Long id) throws Exception {
		NoticeTaskVO vo = this.getById(id);
		return VOConverter.transVo2NoticeTask(vo);
	}

	/**
	* Title: getSendNoticeTask
	* Description:
	* @param id
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeTaskDao#getSendNoticeTask(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public NoticeTask getSendNoticeTask(Long id, Long userId) throws Exception {
		Map<String,Long> paramMap = new HashMap<String,Long>();
		paramMap.put("id", id);
		paramMap.put("userId", userId);
		NoticeTaskVO vo = this.sqlSessionTemplate.selectOne("getNoticeForSend", paramMap);
		if(vo == null){
			vo = this.getById(id);
		}
		if(vo==null){
			return null;
		}
		return VOConverter.transVo2NoticeTask(vo);
	}
	
	/**
	* Title: getSendUserNoticeTask
	* Description:
	* @param taskid
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeTaskDao#getSendUserNoticeTask(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public UserNoticeTask getSendUserNoticeTask(Long taskid, Long userId) throws Exception
	{
		Map<String,Long> paramMap = new HashMap<String,Long>();
		paramMap.put("id", taskid);
		paramMap.put("userId", userId);
		UserNoticeTaskVO vo = this.sqlSessionTemplate.selectOne("getUserNoticeTaskbyUserID", paramMap);
		if(vo==null){
			return null;
		}
		return VOConverter.transVo2UserNoticeTask(vo);
	}
}
