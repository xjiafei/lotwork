package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameOrderDao 
* @Description: GameOrder Dao 
* @author Richard
* @date 2014-1-5 上午11:46:58 
*
 */
public interface IGameOrderDao extends BaseDao<GameOrder>{

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
	* @Title: getOrderByCode 
	* @Description: 根据订单编号获取订单信息
	* @param orderCode
	* @return
	*/
	GameOrder getOrderByCode(String orderCode);
}
