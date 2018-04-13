package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameWarnUserDaoImpl 
* @Description: GameWarnUser Dao　Impl 
* @author hugh
* @date 2014-3-10 下午2:51:30 
*
 */
@Repository("gameWarnUserDaoImpl")
public class GameWarnUserDaoImpl extends BaseIbatis3Dao<GameWarnUser> implements IGameWarnUserDao {

	@Override
	public GameWarnUser queryWarnUserByUserIdAndLotteryIdIssueCode(Long userid, Long lotteryid, Long issueCode) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		
		return this.sqlSessionTemplate.selectOne("queryWarnUserByUserIdAndLotteryIdIssueCode", map);
	}

	/** 
	* @Title: queryGameRiskWarnUser 
	* @Description: 获取风险用户 
	* @param pr  彩种 奖期  状态
	* @return
	*/
	@Override
	public Page<GameWarnUser> queryGameRiskWarnUser(PageRequest<GameRiskWarnUserListQueryRequest> pr) {
		GameRiskWarnUserListQueryRequest gameRiskWarnUserListQueryRequest = pr.getSearchDo();
			
		Map<String, Object> queryParamMap = makeQueryParamMap(gameRiskWarnUserListQueryRequest);		
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getGameRiskWarnUserNumber", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameWarnUser>(0);
		}

		Page<GameWarnUser> page = new Page<GameWarnUser>(pr.getPageNumber(), pr.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameWarnUser> gis = sqlSessionTemplate.selectList("getGameRiskWarnUser", filters, rowBounds);
	
		page.setResult(gis);
		return page;
	
	}
	
	private Map<String, Object> makeQueryParamMap(GameRiskWarnUserListQueryRequest gameRiskWarnUserListQueryRequest) {

		Map<String, Object> queryParamMap = new HashMap<String, Object>();

		if (null != gameRiskWarnUserListQueryRequest.getLotteryId()) {
			queryParamMap.put("lotteryId", gameRiskWarnUserListQueryRequest.getLotteryId());
		}else{
			throw new GameRiskException("lotteryId");
		}
		
		if (null != gameRiskWarnUserListQueryRequest.getIssueCode()) {
			queryParamMap.put("issueCode", gameRiskWarnUserListQueryRequest.getIssueCode());
		}else{
			throw new GameRiskException("issueCode");
		}
		
		if (null != gameRiskWarnUserListQueryRequest.getStatus()) {
			queryParamMap.put("status", gameRiskWarnUserListQueryRequest.getStatus());
		}
		
		return queryParamMap;
	}
}
