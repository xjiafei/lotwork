package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName GameWarnOrderDaoImpl 
* @Description 风险订单 
* @author  hugh
* @date 2014年4月10日 上午10:35:10 
*  
*/
@Repository("gameWarnOrderDaoImpl")
public class GameWarnOrderDaoImpl extends BaseIbatis3Dao<GameWarnOrder> implements IGameWarnOrderDao {

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
	* @Title: updateWarnOrder 
	* @Description: 审核、更新订单
	* @param ids
	* @param status
	*/
	@Override
	public int updateWarnOrder(List<Long> ids, Long status) {
		if(status.longValue() == 1){
			return this.sqlSessionTemplate.update("updatePassByIds",ids);
		}else{
			return this.sqlSessionTemplate.update("updateNotPassByIds",ids);
		}
	}
	
	/** 
	* @Title: updateNotPassByLotteryAndIssue 
	* @Description: 奖期期号所有风控订单不通过
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public int updateNotPassByLotteryAndIssue(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.update("updateNotPassByLotteryAndIssue",map);
		
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
