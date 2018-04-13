package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IGameWarnOrderDao 
* @Description 风险订单 
* @author  hugh
* @date 2014年4月10日 上午10:34:31 
*  
*/
public interface IGameWarnOrderDao extends BaseDao<GameWarnOrder> {
	
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
	Long getOrderWinAmount(String orderCode) throws Exception;
	
	/** 
	* @Title: updateWarnOrder 
	* @Description: 审核、更新订单
	* @param orderIds
	* @param status
	*/
	int updateWarnOrder(List<Long> orderIds, Long status);
	
	/** 
	* @Title: updateNotPassByLotteryAndIssue 
	* @Description: 奖期期号所有风控订单不通过
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	int updateNotPassByLotteryAndIssue(Long lotteryId, Long issueCode);
	
	/** 
	* @Title: updateStatus 
	* @Description: 批处理更新状态
	* @param ids
	* @param status
	* @return
	*/
	int updateStatus(List<Long> ids, Long status);
}
