package com.winterframework.firefrog.shortlived.poolking.service;

import java.util.List;

import com.winterframework.firefrog.shortlived.poolking.dao.vo.LeaderboardVO;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;

public interface ILeaderboardService {

	public List<LeaderboardVO> queryScoresHistory(MonkeyHistoryRequest pool);
}
