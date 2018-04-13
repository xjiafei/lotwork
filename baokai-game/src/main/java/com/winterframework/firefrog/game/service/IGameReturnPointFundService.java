package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.entity.GameReturnPoint;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;


/** 
* @ClassName IGameReturnPointFundService 
* @Description 返点资金接口 
* @author  hugh
* @date 2014年4月22日 上午10:38:32 
*  
*/
public interface IGameReturnPointFundService {
	
	/**
	 * 冻结
	 * @param ctx
	 * @param order
	 * @param retPoint
	 * @return
	 * @throws GameException
	 */
	int freeze(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws GameException;
	/**
	 * 派发
	 * @param ctx
	 * @param order
	 * @param retPoint
	 * @return
	 * @throws GameException
	 */
	int distribute(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws GameException;
	/**
	 * 撤销
	 * @param ctx
	 * @param order
	 * @param retPoint
	 * @return
	 * @throws GameException
	 */
	int cancel(GameContext ctx,GameOrder order,GameRetPoint retPoint) throws GameException;
	
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 派发资金返点  并更新返点表 
	* @param orderId
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Long orderId) throws GameRiskException;

	boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	
	
	
}
