package com.winterframework.firefrog.fund.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.fund.dao.IBypassConfigDao;
import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BypassConfigDaoImpl extends BaseIbatis3Dao<BypassConfigVO>
		implements IBypassConfigDao {
	@Override
	public Optional<List<BypassConfigVO>> findByCondition(BypassConfigVO entity) {
		List<BypassConfigVO> charges =  this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"),entity);
		return Optional.fromNullable(charges);
	}
	
	@Override
	public Integer udpateBypass(BypassConfigVO vo){
		Map<String,Object> params =Maps.newHashMap();
		params.put("singleLowlimit", vo.getSingleLowlimit());
		params.put("singleUplimit", vo.getSingleUplimit());
		params.put("dailyUplimit", vo.getDailyUplimit());
		params.put("isOpen",vo.getIsOpen());
		params.put("id",vo.getId());
		return this.sqlSessionTemplate.update(this.getQueryPath("update"), params);
	}
	

	
}
