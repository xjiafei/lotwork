package com.winterframework.firefrog.game.service;

import java.util.Collection;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName IGameOrderFundService 
* @Description 订单相关请求审核 系统
* @author  hugh
* @date 2014年4月28日 上午10:11:25 
*  
*/
/** 
* @ClassName IGameOrderFundService 
* @Description TODO 
* @author  hugh
* @date 2014年5月6日 下午2:07:32 
*  
*/
/** 
* @ClassName IGameOrderFundService 
* @Description TODO 
* @author  hugh
* @date 2014年5月6日 下午2:08:13 
*  
*/
public interface IGameOrderFundService {
	/** 
	* @Title: orderFund 
	* @Description: 投注扣款
	* @param collection
	* @throws GameRiskException
	*/
	void orderFund(Collection<GameOrder> collection) throws GameRiskException;
	void orderFund(GameContext ctx,Collection<GameOrder> collection) throws GameRiskException;

	/** 
	* @Title: orderFreeze 
	* @Description: 投注冻结
	* @param orders
	* @throws GameRiskException
	*/
	void orderFreeze(Collection<GameOrder> orders) throws GameRiskException;

	/** 
	* @Title: orderCancel 
	* @Description: 投注撤销
	* @param orders
	* @throws GameRiskException
	*/
	void orderCancel(Collection<GameOrder> orders) throws GameRiskException;

	/** 
	* @Title: orderFund 
	* @Description: 投注扣款
	* @param orders
	* @throws GameRiskException
	*/
	void orderFund(GameOrder order) throws GameRiskException;
	void orderFund(GameContext ctx,GameOrder order) throws GameRiskException;

	/** 
	* @Title: orderFreeze 
	* @Description: 投注冻结
	* @param orders
	* @throws GameRiskException
	*/
	void orderFreeze(GameOrder order) throws GameRiskException;

	/** 
	* @Title: orderCancel 
	* @Description: 投注撤销
	* @param orders
	* @throws GameRiskException
	*/
	void orderCancel(GameOrder order) throws GameRiskException;
	
	
	/** 
	* @Title: packageOrderFundTORiskDTO 
	* @Description: 封装订单请求审核系统资金接口请求数据
	* @param order
	* @return
	*/
	TORiskDTO packageOrderFundTORiskDTO(GameOrder order);
	
	TORiskDTO packageOrderFreezeTORiskDTO(GameOrder order);
	
	TORiskDTO packageOrderCancelTORiskDTO(GameOrder order);
	
	TORiskDTO packageOrderUnFreezeTORiskDTO(GameOrder order);
	
	/** 
	* @Title: updateOrderCancelStatus 
	* @Description: 更新订单状态
	* @param gameOrder
	*/
	void updateOrderCancelStatus(GameOrder gameOrder);
	
	void updateOrderFreezeStatus(GameOrder gameOrder);
	
		
	TORiskDTO packageOrderFundTORiskDTO(Long orderId);
	
	TORiskDTO packageOrderFreezeTORiskDTO(Long orderId);
	
	TORiskDTO packageOrderCancelTORiskDTO(Long orderId);
	
	TORiskDTO packageOrderUnFreezeTORiskDTO(Long orderId);
	
	void updateOrderCancelStatus(Long orderId);
	
	void updateOrderCancelStatus(Collection<Long> orderIds);
	
	void updateOrderCancelStatus(List<GameOrder> gameOrders);
	
	void updateOrderFreezeStatus(List<GameOrder> gameOrders);
	
	
	/////////////////////new///////////////////////////
	public int freeze(GameContext ctx,GameOrder order) throws Exception;
	public int unfreeze(GameContext ctx,GameOrder order) throws Exception;
	public int pay(GameContext ctx,GameOrder order) throws Exception;
	public int unpay(GameContext ctx,GameOrder order) throws Exception; 
}
