package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;
import com.winterframework.firefrog.game.web.dto.GameRiskIssueListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueDaoImpl")
public class GameIssueDaoImpl extends BaseIbatis3Dao<GameIssue> implements IGameIssueDao {

	@Override
	public GameIssue getGameIssueByIssueCode(Long issueCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("getGameIssueByGameIssueCode", issueCode);
	}

	@Override
	public GameRiskIssue getGameRiskIssue(Long lotteryId,Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		GameRiskIssue dbIssue = this.sqlSessionTemplate.selectOne("getGameRiskIssue", map);		
		return dealRiskStatus(dbIssue);
	}

	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询审核的奖期
	* @param pr
	* @return
	*/
	@Override
	public Page<GameRiskIssue> queryRiskGameIssues(PageRequest<GameRiskIssueListQueryRequest> pr) {
		GameRiskIssueListQueryRequest gameIssueListQueryRequest = pr.getSearchDo();	

		Map<String, Object> queryParamMap = makeQueryParamMap(gameIssueListQueryRequest);			
		
		return queryPastIssue(pr, queryParamMap);
	
	}

	private Page<GameRiskIssue> queryPastIssue(PageRequest<GameRiskIssueListQueryRequest> pr,
			Map<String, Object> queryParamMap) {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getGameRiskIssuesNumber", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameRiskIssue>(0);
		}

		Page<GameRiskIssue> page = new Page<GameRiskIssue>(pr.getPageNumber(), pr.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameRiskIssue> gis = sqlSessionTemplate.selectList("getGameRiskIssues", filters, rowBounds);

		for (GameRiskIssue gi : gis) {
			gi = dealRiskStatus(gi);
		}
		
		page.setResult(gis);
		return page;
	}

	private Map<String, Object> makeQueryParamMap(GameRiskIssueListQueryRequest gameRiskIssueListQueryRequest) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (null != gameRiskIssueListQueryRequest.getLotteryId()) {
			map.put("lotteryId", gameRiskIssueListQueryRequest.getLotteryId());
		}
		
		if (null != gameRiskIssueListQueryRequest.getStatus()) {
			map.put("status", gameRiskIssueListQueryRequest.getStatus());
		}
		
		//当传过来起始时间段为null 则根据规则设置相应时间
		if (null != gameRiskIssueListQueryRequest.getShowStartTime()) {
			//			map.put("startTime", DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime()));
			map.put("startTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(gameRiskIssueListQueryRequest.getShowStartTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("startTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), -365),
					DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if (null != gameRiskIssueListQueryRequest.getShowEndTime()) {
			//			map.put("endTime", DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime()));
			map.put("endTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(gameRiskIssueListQueryRequest.getShowEndTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("endTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), 1),
					DateUtils.DATETIME_FORMAT_PATTERN));
		}
		return map;
	}
	
	private GameRiskIssue dealRiskStatus(GameRiskIssue gi){
		if(gi==null){
			return gi;
		}
		if(gi.getRiskWarnOrderNumber()== 0){
			gi.setRiskStatus(0);
		}else if(gi.getRiskWarnOrderNumber()== gi.getRiskDealedWarnOrderNumber()){
			gi.setRiskStatus(1);
		}else{
			gi.setRiskStatus(2);
		}
		return gi;
	}

	/**
	* Title: setOperator
	* Description:
	* @param gameIssue
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameIssueDao#setOperator(com.winterframework.firefrog.game.dao.vo.GameIssue) 
	*/
	@Override
	public void setOperator(GameRiskIssue gameIssue) throws Exception {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameIssue.setOperator", gameIssue);
		
	}

}
