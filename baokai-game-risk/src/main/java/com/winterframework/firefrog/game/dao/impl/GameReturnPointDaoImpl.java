package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameReturnPointDaoImpl")
public class GameReturnPointDaoImpl extends BaseIbatis3Dao<GameRetPoint> implements IGameReturnPointDao {

	@Override
	public List<GameRetPoint> getGameRetPointByStatus(Integer status) {
		return this.sqlSessionTemplate.selectList("getGameRetPointByStatus", status);
	}

	@Override
	public List<GameRetPoint> getGameRetPointByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getGameRetPointByIssueCode", issueCode);
	}
	
	@Override
	public List<GameRetPoint> getGameRetPointByLotteryIssue(Long lotteryId ,Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("issueCode", issueCode);
		map.put("lotteryId", lotteryId);
		
		return this.sqlSessionTemplate.selectList("getGameRetPointByLotteryIssue", map);
	}
	
	@Override
	public GameRetPoint getGameRetPointByGameOrderId(Long gameOrderId) {
		return this.sqlSessionTemplate.selectOne("getGameRetPointByGameOrderId", gameOrderId);
	}

	@Override
	public Long getRetPointTotalAmountByOrderId(Long id) throws Exception {
		return this.sqlSessionTemplate.selectOne("getRetPointTotalAmountByOrderId", id);
	}

	@Override
	public GameRetPoint getRetPointByOrderId(Long id) {
		return this.sqlSessionTemplate.selectOne("getRetPointByOrderId", id);
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
}
