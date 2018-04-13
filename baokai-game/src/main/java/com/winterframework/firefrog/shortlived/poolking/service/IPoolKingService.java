package com.winterframework.firefrog.shortlived.poolking.service;

import java.util.List;

import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.modules.web.jsonresult.Pager;

public interface IPoolKingService {

	public List<PoolKingScore> queryPoolKingScore(PoolKingScoreRequest param,
			Pager pager);

	public PoolKingScore queryPoolKingUserScore(PoolKingScoreRequest param,
			Long userId);

	public List<PoolKingScore> queryMonkeyActivityScore(PoolKingScoreRequest param,
			Pager pager);
}
