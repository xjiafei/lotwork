package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.beginmession.dao.BeginAwardLogDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginAwardLogDaoImpl extends BaseIbatis3Dao<BeginAwardLog> implements BeginAwardLogDao{

	@Override
	public List<BeginAwardLog> findByCondition(BeginAwardLog awardLong) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), awardLong);
	}

	
	@Override
	public BeginAwardLog findFirstByCondition(BeginAwardLog awardLong) {
		BeginAwardLog awardLog = null;
		List<BeginAwardLog> lists= this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), awardLong);
		if(lists!=null && !lists.isEmpty()){
			awardLog = lists.get(0);
		}
		return awardLog;
	}
}
