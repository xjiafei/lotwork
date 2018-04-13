package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameWarnOrderDao extends BaseDao<GameWarnOrder> {
	
	void deleteByLotteryIssue(Long lotteryid, Long issueCode);
	
	/**
	 * 
	* @Title: isExistWarnOrder 
	* @Description: 是否存在警告订单
	* @param lotteryid
	* @param orderid
	* @return
	 */
	Long isExistWarnOrder(Integer lotteryid, Long orderid);

	/**
	 * 
	* @Title: getOrderWinAmount 
	* @Description:根据OrderCode 获取订单中奖金额。 
	* @param orderCode
	* @return
	* @throws Exception
	 */
	public Long getOrderWinAmount(String orderCode) throws Exception;
	
	/** 
	* @Title: updateStatus 
	* @Description: 批处理更新状态
	* @param orderIds
	* @param status
	* @return
	*/
	int updateStatus(List<Long> orderIds, Long status);
}
