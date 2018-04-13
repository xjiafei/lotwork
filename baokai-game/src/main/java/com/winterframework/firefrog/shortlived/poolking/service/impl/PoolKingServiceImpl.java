package com.winterframework.firefrog.shortlived.poolking.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.shortlived.poolking.dao.IPoolKingDao;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.PoolKingRequestVo;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.service.IPoolKingService;
import com.winterframework.modules.web.jsonresult.Pager;

@Service("poolKingServiceImpl")
public class PoolKingServiceImpl implements IPoolKingService {

	private Logger logger = LoggerFactory.getLogger(PoolKingServiceImpl.class);

	@Resource(name = "poolKingDaoImpl")
	private IPoolKingDao poolKingDao;

	@Override
	public List<PoolKingScore> queryPoolKingScore(PoolKingScoreRequest param,
			Pager pager) {
		PoolKingRequestVo request = new PoolKingRequestVo();
		request.setStartNo(pager.getStartNo());
		request.setEndNo(pager.getEndNo());
		request.setStartDate(param.getStartDate());
		request.setEndDate(param.getEndDate());
		return poolKingDao.queryPoolKingScore(request);
	}

	@Override
	public PoolKingScore queryPoolKingUserScore(PoolKingScoreRequest param,
			Long userId) {
		PoolKingRequestVo request = new PoolKingRequestVo();
		request.setUserId(userId);
		request.setStartDate(param.getStartDate());
		request.setEndDate(param.getEndDate());
		return poolKingDao.queryPoolKingUserScore(request);
	}

	@Override
	public List<PoolKingScore> queryMonkeyActivityScore(
			PoolKingScoreRequest param, Pager pager) {
		PoolKingRequestVo request = new PoolKingRequestVo();
		request.setStartDate(param.getStartDate());
		request.setEndDate(param.getEndDate());
		return poolKingDao.queryMonkeyActivityScore(request);
	}

}
