package com.winterframework.firefrog.shortlived.poolking.dao;

import java.util.List;

import com.winterframework.firefrog.shortlived.poolking.dao.vo.LeaderboardVO;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.MonkeyHistoryRequest;

public interface ILeaderboardDao {

	public List<LeaderboardVO> queryScoresHistory(MonkeyHistoryRequest request);
}
