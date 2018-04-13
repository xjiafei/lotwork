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
	* @Description: 资金返点
	* @param orderList
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders) throws GameRiskException;
	boolean returnPointFundUpdateRetsStatus(GameContext ctx,Map<Long, GameOrder> orders) throws GameRiskException;
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 派发资金返点  并更新返点表 
	* @param orderId
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Long orderId) throws GameRiskException;
	boolean returnPointFundUpdateRetsStatus(GameContext ctx,Long orderId) throws GameRiskException;
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 派发资金返点  并更新返点表 
	* @param orders
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(List<GameOrder> orders) throws GameRiskException;
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 资金返点  并更新 返点表状态
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	boolean returnPointFundUpdateRetsStatus(GameContext ctx,Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	/** 
	* @Title: returnPointFreeze 
	* @Description: 冻结资金返点  并更新返点表 
	* @param orders
	* @return
	*/
	void returnPointFreeze(Map<Long, GameOrder> orders) throws GameRiskException;
	/** 
	* @Title: returnPointFreeze 
	* @Description: 冻结资金返点  并更新返点表 
	* @param orderId
	* @return
	*/
	void returnPointFreeze(Long orderId);
	/** 
	* @Title: returnPointFreeze 
	* @Description: 冻结资金返点  并更新返点表 
	* @param orderIds
	* @return
	*/
	void returnPointFreeze(List<Long> orderIds);
	
	/** 
	* @Title: returnPointCancel 
	* @Description: 撤销资金返点  并更新返点表 
	* @param orderList
	* @return
	*/
	boolean returnPointCancel(List<GameOrder> orderList) throws GameRiskException;
	
	/** 
	* @Title: returnPointFund 
	* @Description: 资金返点 不带更新状态
	* @param orderIds
	* @return 
	* @throws GameRiskException
	*/
	boolean returnPointFund(List<Long> orderIds)  throws GameRiskException;
	
	/** 
	* @Title: returnPointFund 
	* @Description: 资金返点 不带更新状态
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFund(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	boolean returnPointFund(GameContext ctx,Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	void returnPointFund(GameContext ctx,GameOrder order) throws GameRiskException;
	
	TORiskDTO packageFundTORiskDTO(GameOrder order);

	TORiskDTO packageCancelTORiskDTO(GameOrder order);
	
	void updateRetPointCancelStatus(Long orderId);
	
	void updateRetPointCancelStatus(List<Long> orderIds);
	
	void updateRetPointFreeznStatus(List<Long> orderIds);
	
	void updateRetPointFreeznStatus(Long orderId);
}
