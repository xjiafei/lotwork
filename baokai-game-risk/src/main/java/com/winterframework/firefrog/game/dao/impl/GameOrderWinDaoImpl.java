package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameOrderWinDaoImpl 
* @Description: 中奖订单Dao
* @author Alan
* @date 2013-11-19 下午1:54:16 
*  
*/
@Repository("gameOrderWinDaoImpl")
public class GameOrderWinDaoImpl extends BaseIbatis3Dao<GameOrderWin> implements IGameOrderWinDao {

	/**
	* Title: updateGameOrderWins
	* Description:
	* @param map
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameOrderWinDao#updateGameOrderWins(java.util.Map) 
	*/
	@Override
	public void updateGameOrderWins(Map<String, Object> map) throws Exception {
		this.sqlSessionTemplate.update("updateWinOrders", map);
	}

	@Override
	public List<GameOrderWin> getGameOrderWinsByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getGameOrderWinsByLotteryIdAndIssueCode", map);
	}

	@Override
	public GameOrderWin selectGameOrderWinByOrderId(Long orderId) {
		return this.sqlSessionTemplate.selectOne("getOrderWinByOrderId", orderId);
	}
	
	/**
	* @Title: updateStatus
	* @Description: 通过orderId更新状态
	* @param orderIds
	* @param status
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGameWarnOrderDao#updateStatus(java.util.List, java.lang.Long) 
	*/
	public int updateStatus(List<Long> orderIds, Long status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("list", orderIds);
		return this.sqlSessionTemplate.update("updateStatus",map);
	}
	

	/** 
	* @Title: getWinCounts 
	* @Description: 一期的中奖总额，排除撤销的
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public Long getWinCounts(Long lotteryId, Long issueCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getWinCounts", map);
	}
}
