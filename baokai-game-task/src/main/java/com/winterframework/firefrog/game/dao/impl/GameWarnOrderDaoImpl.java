package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameWarnOrderDaoImpl")
public class GameWarnOrderDaoImpl extends BaseIbatis3Dao<GameWarnOrder> implements IGameWarnOrderDao {

	/**
	* @Title: deleteByLotteryIssue
	* @Description:删除一期数据
	* @param lotteryid
	* @param issueCode 
	*/
	@Override
	public void deleteByLotteryIssue(Long lotteryid, Long issueCode) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		this.sqlSessionTemplate.delete(getQueryPath("deleteByLotteryIssue"), map);
	}

	@Override
	public Long isExistWarnOrder(Integer lotteryid, Long orderid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("orderid", orderid);
		return this.sqlSessionTemplate.selectOne("isExistWarnOrder", map);
	}

	@Override
	public Long getOrderWinAmount(String orderCode) throws Exception {
		return this.sqlSessionTemplate.selectOne("getOrderWinAmountbyOrderCode", orderCode);
	}

	/**
	* @Title: updateStatus
	* @Description: 通过orderId更新状态
	* @param orderIds
	* @param status
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGameWarnOrderDao#updateStatus(java.util.List, java.lang.Long) 
	*/
	public int updateStatus(List<Long> orderIds, Long status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("list", orderIds);
		return this.sqlSessionTemplate.update(getQueryPath("updateStatus"), map);
	}
}
