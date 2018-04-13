package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameTrendJbylDaoImpl 
* @Description: 号码走势分析表 DAO 实现类
* @author Denny
* @date 2014-4-1 下午4:15:08 
*
 */
@Repository("gameTrendJbylDaoImpl")
public class GameTrendJbylDaoImpl extends BaseIbatis3Dao<GameTrendJbyl> implements IGameTrendJbylDao {
	
	@Override
	public List<GameTrendJbyl> getTrendJbyl(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode,
			Integer trendType, int num) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}
		if (gameSetCode != null) {
			map.put("gameSetCode", gameSetCode);
		}
		if (trendType != null) {
			map.put("trendType", trendType);
		}
		map.put("num", num);

		return this.sqlSessionTemplate.selectList("getTrendJbyl", map);
	}

	@Override
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (lotteryId != null) {
			map.put("lotteryId", lotteryId);
		}
		if (gameGroupCode != null) {
			map.put("gameGroupCode", gameGroupCode);
		}
		if (gameSetCode != null) {
			map.put("gameSetCode", gameSetCode);
		}
		if (betMethodCode != null) {
			map.put("betMethodCode", betMethodCode);
		}
		map.put("num", num);

		return this.sqlSessionTemplate.selectList("getTrendJbyl", map);
	}
	
}
