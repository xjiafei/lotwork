package com.winterframework.firefrog.subsys.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.subsys.dao.ISubSystemActivityLogDao;
import com.winterframework.firefrog.subsys.vo.SubSystemActivityLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("SubSystemActivityLogDaoImpl")
public class SubSystemActivityLogDaoImpl extends BaseIbatis3Dao<SubSystemActivityLog> implements ISubSystemActivityLogDao {


	@Override
	public Long querySubSystemActivityLogCount(SubSystemActivityLog systemActivityLog) {

		Map<String, Object> map =new HashMap<String, Object>();
		map.put("account", systemActivityLog.getAccount());
		map.put("roundId", systemActivityLog.getRoundId());
		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("querySubsysActivityLog"), map);

	}
	
	@Override
	public SubSystemActivityLog querySubSystemActivityLog(SubSystemActivityLog systemActivityLog) {

		Map<String, Object> map =new HashMap<String, Object>();
		map.put("account", systemActivityLog.getAccount());
		map.put("roundId", systemActivityLog.getRoundId());
		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("querySubsysActivity"), map);

	}

	@Override
	public void saveSubSystemActivityLog(SubSystemActivityLog systemActivityLog) {
		insert(systemActivityLog);

	}

}
