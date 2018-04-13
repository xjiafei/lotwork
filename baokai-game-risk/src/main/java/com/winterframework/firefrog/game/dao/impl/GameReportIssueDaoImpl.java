package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameReportIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameReportIssue;
import com.winterframework.firefrog.game.web.dto.GameRiskReportRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameReportIssueDaoImpl")
public class GameReportIssueDaoImpl extends BaseIbatis3Dao<GameReportIssue> implements IGameReportIssueDao {

	/** 
	* @Title: queryRiskGameIssues 
	* @Description: 查询报表list 
	* @param pr
	* @return
	*/
	public Page<GameReportIssue> queryGameReportIssues(PageRequest<GameRiskReportRequest> pr){
		GameRiskReportRequest request = pr.getSearchDo();	

		Map<String, Object> queryParamMap = makeQueryParamMap(request);			
		
		return queryPage(pr, queryParamMap);
	}
	
	private Page<GameReportIssue> queryPage(PageRequest<GameRiskReportRequest> pr, Map<String, Object> queryParamMap) {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountReportByPage", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameReportIssue>(0);
		}

		Page<GameReportIssue> page = new Page<GameReportIssue>(pr.getPageNumber(), pr.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameReportIssue> gis = sqlSessionTemplate.selectList("getReportByPage", filters, rowBounds);
	
		page.setResult(gis);
		return page;
	}

	private Map<String, Object> makeQueryParamMap(GameRiskReportRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (request!=null && null != request.getLotteryId()) {
			map.put("lotteryId", request.getLotteryId());
		}else{
			map.put("lotteryId", null);
		}
		
		if (request!=null && null != request.getIssueCode()) {
			map.put("webIssueCode", request.getIssueCode());
		}else{
			map.put("webIssueCode", null);
		}
		
		//当传过来起始时间段为null 则根据规则设置相应时间
		if (request!=null &&null != request.getShowStartTime()) {
			map.put("startTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(request.getShowStartTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("startTime", null);
		}
		if (request!=null && null != request.getShowEndTime()) {
			map.put("endTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(request.getShowEndTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("endTime",null);
		}
		return map;
	}
	
	public GameReportIssue getGameReportIssue(Long lotteryId, Long issueCode){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);	
		map.put("issueCode", issueCode);
		return sqlSessionTemplate.selectOne("getGameReportIssue", map);
	}
	
	
}
