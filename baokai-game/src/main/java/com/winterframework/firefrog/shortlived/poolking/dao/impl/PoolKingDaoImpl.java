package com.winterframework.firefrog.shortlived.poolking.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.poolking.dao.IPoolKingDao;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.PoolKingRequestVo;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("poolKingDaoImpl")
public class PoolKingDaoImpl  extends BaseIbatis3Dao<PoolKingRequestVo> implements IPoolKingDao{

	@Override
	public List<PoolKingScore> queryPoolKingScore(PoolKingRequestVo request) {
		return sqlSessionTemplate.selectList("queryPoolKingScore", request);
	}

	@Override
	public PoolKingScore queryPoolKingUserScore(PoolKingRequestVo request) {
		return sqlSessionTemplate.selectOne("queryPoolKingUserScore", request);
	}

	@Override
	public List<PoolKingScore> queryMonkeyActivityScore(PoolKingRequestVo request) {
		return sqlSessionTemplate.selectList("queryMonkeyActivityScore", request);
	}

}
