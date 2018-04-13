package com.winterframework.firefrog.shortlived.poolking.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.shortlived.poolking.dao.ILeaderboardDao;
import com.winterframework.firefrog.shortlived.poolking.dao.IPoolKingDao;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.LeaderboardVO;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.service.ILeaderboardService;

@Service("leaderboardServiceImpl")
public class LeaderboardServiceImpl implements ILeaderboardService{

	
	@Resource(name = "leaderboardDaoImpl")
	private ILeaderboardDao leaderboardDao;
	
	@Override
	public List<LeaderboardVO> queryScoresHistory(MonkeyHistoryRequest request) {
		return leaderboardDao.queryScoresHistory(request);
	}

}
