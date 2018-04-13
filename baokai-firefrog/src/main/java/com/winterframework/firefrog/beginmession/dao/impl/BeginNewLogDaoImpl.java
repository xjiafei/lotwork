package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.beginmession.dao.BeginNewLogDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginNewLogDaoImpl extends BaseIbatis3Dao<BeginNewLog> implements
		BeginNewLogDao {

	@Override
	public List<BeginNewLog> findByCondition(BeginNewLog log) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), log);
	}

	@Override
	public Page<BeginNewLog> selectByPage(
			PageRequest<BeginNewLog> pageRequest) throws Exception {
		pageRequest.setSortColumns("LOG_TIME DESC");
		return this.getAllByPage(pageRequest);
	}


}
