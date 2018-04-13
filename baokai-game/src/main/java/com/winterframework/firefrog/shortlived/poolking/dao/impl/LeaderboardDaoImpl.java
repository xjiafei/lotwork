package com.winterframework.firefrog.shortlived.poolking.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.poolking.dao.ILeaderboardDao;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.LeaderboardVO;
import com.winterframework.firefrog.shortlived.poolking.dao.vo.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("leaderboardDaoImpl")
public class LeaderboardDaoImpl extends BaseIbatis3Dao<LeaderboardVO> implements ILeaderboardDao{

	@Override
	public List<LeaderboardVO> queryScoresHistory(MonkeyHistoryRequest request) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("id", request.getUserId());
		filters.put("stardDate", request.getStartDate());
		filters.put("endDate", request.getEndDate());
		return sqlSessionTemplate.selectList("queryScoresHistory", filters);
	}

}
