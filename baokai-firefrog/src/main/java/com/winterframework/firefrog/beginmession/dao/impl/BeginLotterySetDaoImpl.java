package com.winterframework.firefrog.beginmession.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.beginmession.dao.BeginLotterySetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginLotterySetDaoImpl extends BaseBeginNewDao<BeginLotterySet> implements BeginLotterySetDao{

	@Override
	public BeginLotterySet findById(Long id) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("findById"), id);
	}

}
