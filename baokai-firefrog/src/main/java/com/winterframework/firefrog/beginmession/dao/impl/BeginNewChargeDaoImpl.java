package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.BeginNewChargeDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;

@Repository
public class BeginNewChargeDaoImpl extends BaseBeginNewDao<BeginNewCharge> implements
		BeginNewChargeDao {

	@Override
	public List<BeginNewCharge> findMaxVersionAndRowNum() {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findMaxVersionAndRowNum"));
	}

	@Override
	public Optional<List<BeginNewCharge>> findByCondition2(BeginNewCharge entity) {
		List<BeginNewCharge> charges =  this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition2"),entity);
		return Optional.fromNullable(charges);
	}

}
