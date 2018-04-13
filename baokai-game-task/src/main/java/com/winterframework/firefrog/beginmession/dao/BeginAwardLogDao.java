package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginAwardLogDao extends BaseDao<BeginAwardLog>{

	public List<BeginAwardLog> findByCondition(BeginAwardLog awardLong);
	
	public BeginAwardLog findFirstByCondition(BeginAwardLog awardLong);
}
