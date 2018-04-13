package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.exception.GameRiskException;


/** 
* @ClassName IGameReturnPointFundService 
* @Description 返点资金接口 
* @author  hugh
* @date 2014年4月22日 上午10:38:32 
*  
*/
public interface IGameReturnPointFundService {

	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 资金返点
	* @param orderList
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders) throws GameRiskException;
	
	/** 
	* @Title: returnPointFreeze 
	* @Description: 冻结资金返点  并更新返点表 
	* @param orders
	* @return
	*/
	boolean returnPointFreeze(Map<Long, GameOrder> orders) throws GameRiskException;
	
	/** 
	* @Title: returnPointCancel 
	* @Description: 撤销资金返点  并更新返点表 
	* @param orderList
	* @return
	*/
	boolean returnPointCancel(List<GameOrder> orderList) throws GameRiskException;
	
	/** 
	* @Title: returnPointFund 
	* @Description: 资金返点
	* @param orderIds
	* @return 
	* @throws GameRiskException
	*/
	boolean returnPointFund(List<Long> orderIds)  throws GameRiskException;
	
	/** 
	* @Title: returnPointFund 
	* @Description: 资金返点
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFund(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
	
	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 资金返点  并更新 返点表状态
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException
	*/
	boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException;
}
