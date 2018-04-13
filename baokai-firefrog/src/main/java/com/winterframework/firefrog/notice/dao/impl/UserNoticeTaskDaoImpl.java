/**   
* @Title: UserNoticeTaskDaoImpl.java 
* @Package com.winterframework.firefrog.notice.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 上午10:34:25 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao;
import com.winterframework.firefrog.notice.dao.vo.UserNoticeTaskVO;
import com.winterframework.firefrog.notice.dao.vo.VOConverter;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: UserNoticeTaskDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 上午10:34:25 
*  
*/
@Repository("userNoticeTaskDaoImpl")
public class UserNoticeTaskDaoImpl extends BaseIbatis3Dao<UserNoticeTaskVO> implements IUserNoticeTaskDao{

	/**
	* Title: update
	* Description:
	* @param task
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao#update(com.winterframework.firefrog.notice.entity.UserNoticeTask) 
	*/
	@Override
	public void update(UserNoticeTask task) throws Exception {
		this.update(VOConverter.transUserNoticeTask2Vo(task));
	}

	/**
	* Title: save
	* Description:
	* @param task
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao#save(com.winterframework.firefrog.notice.entity.UserNoticeTask) 
	*/
	@Override
	public UserNoticeTask save(UserNoticeTask task) throws Exception {
		UserNoticeTaskVO vo = VOConverter.transUserNoticeTask2Vo(task);
		this.insert(vo);
		return VOConverter.transVo2UserNoticeTask(vo);
	}

	/**
	* Title: queryUserNoticeTaskByProperties
	* Description:
	* @param task
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.IUserNoticeTaskDao#queryUserNoticeTaskByProperties(com.winterframework.firefrog.notice.entity.UserNoticeTask) 
	*/
	@Override
	public List<UserNoticeTask> queryUserNoticeTaskByProperties(UserNoticeTask task) throws Exception {
		List<UserNoticeTaskVO> vos = this.sqlSessionTemplate.selectList("com.winterframework.firefrog.notice.dao.vo.UserNoticeTaskVO.getByPage", VOConverter.transUserNoticeTask2Vo(task));
		List<UserNoticeTask> tasks = new ArrayList<UserNoticeTask>();
		for (UserNoticeTaskVO vo : vos) {
			tasks.add(VOConverter.transVo2UserNoticeTask(vo));
		}
		return tasks;
	}
	public void deleteByUser(Long userId) throws Exception{
		this.sqlSessionTemplate.delete("com.winterframework.firefrog.notice.dao.vo.UserNoticeTaskVO.deleteByUser", userId);

	}


}
