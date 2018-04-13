package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.exception.GameRiskException;

/** 
* @ClassName IGameDistributeFundService 
* @Description 派奖资金接口 
* @author  hugh
* @date 2014年4月22日 上午10:38:32 
*  
*/
public interface IGameOrderWinFundService {

	boolean orderWinFund(GameOrderWin orderWin)  throws GameRiskException;
	
	boolean orderWinFund(GameOrder order, GameOrderWin orderWin)  throws GameRiskException;
	
	boolean orderWinFund(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	/** 
	* @Title: orderWinFundUpdateWinsStatus 
	* @Description: 中奖派奖 并更新中奖表
	* @param orders
	* @param orderWins
	* @return
	* @throws GameRiskException
	*/
	boolean orderWinFundUpdateWinsStatus(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	/** 
	* @Title: orderWinFreeze 
	* @Description: 中奖表冻结中奖订单
	* @param orderWins
	* @return
	* @throws GameRiskException
	*/
	boolean orderWinFreeze(List<GameOrderWin> orderWins)  throws GameRiskException;
}
