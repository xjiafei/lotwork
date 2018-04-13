package com.winterframework.firefrog.beginmession.dao;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionTaskLog;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.TaskStatus;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginMissionTaskLogDao extends BaseDao<BeginMissionTaskLog>{

	public void updateStatus(Long taskId,TaskStatus status);
	
}
