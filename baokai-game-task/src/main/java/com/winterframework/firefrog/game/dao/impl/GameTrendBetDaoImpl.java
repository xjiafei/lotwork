package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameTrendBetDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
 
/**
 * 开奖号码走势图投注页DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
@Repository("gameTrendBetDaoImpl")
public class GameTrendBetDaoImpl extends BaseIbatis3Dao<GameTrendBet> implements IGameTrendBetDao {
	@Override
	public GameTrendBet getByLotteryIdAndBetTypeAndType(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode,Integer type)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("groupCode", groupCode);
		map.put("setCode", setCode);
		map.put("methodCode", methodCode);
		map.put("type", type);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLotteryIdAndBetTypeAndType"), map);
	} 
	@Override
	public List<GameTrendBet> getByLotteryId(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("getByLotteryId"), lotteryId);
	}
}
