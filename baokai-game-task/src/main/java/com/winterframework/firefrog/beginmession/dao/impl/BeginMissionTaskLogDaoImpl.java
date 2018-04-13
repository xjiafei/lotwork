package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginMissionTaskLogDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionTaskLog;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.TaskStatus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginMissionTaskLogDaoImpl extends BaseIbatis3Dao<BeginMissionTaskLog> implements BeginMissionTaskLogDao{

	@Override
	public void updateStatus(Long taskId, TaskStatus status) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("id", taskId);
		map.put("status", status.getValue());
		map.put("finishTime", new Date());		
		this.sqlSessionTemplate.update(this.getQueryPath("updateStatus"), map);
	}

}
