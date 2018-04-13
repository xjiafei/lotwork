package com.winterframework.firefrog.shortlived.poolking.dao;

import java.util.List;

import com.winterframework.firefrog.shortlived.poolking.dao.vo.PoolKingRequestVo;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;

public interface IPoolKingDao {

	public List<PoolKingScore> queryPoolKingScore(PoolKingRequestVo request);

	public PoolKingScore queryPoolKingUserScore(PoolKingRequestVo request);

	public List<PoolKingScore> queryMonkeyActivityScore(PoolKingRequestVo request);

}
