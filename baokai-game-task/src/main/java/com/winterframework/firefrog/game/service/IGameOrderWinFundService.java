package com.winterframework.firefrog.game.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName IGameOrderWinFundService 
* @Description 中奖订单及其派奖资金接口 
* @author  hugh
* @date 2014年4月22日 上午10:38:32 
*  
*/
public interface IGameOrderWinFundService {

	boolean orderWinFund(GameOrderWin orderWin)  throws GameRiskException;
	boolean orderWinFund(GameOrder order) throws GameRiskException;
	boolean orderWinFundAndUpdateWinStatus(GameOrder order) throws GameRiskException;
	boolean orderWinFund(GameOrder order, GameOrderWin orderWin)  throws GameRiskException;
	
	boolean orderWinFund(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	boolean orderWinFund(GameContext ctx,Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	void orderWinFund(GameContext ctx,GameOrder order, GameOrderWin orderWin)throws GameRiskException;
	void orderWinFund(GameContext ctx,GameOrder order)throws GameRiskException;
	/** 
	* @Title: orderWinFundUpdateWinsStatus 
	* @Description: 中奖派奖 并更新中奖表
	* @param orders
	* @param orderWins
	* @return
	* @throws GameRiskException
	*/
	boolean orderWinFundUpdateWinsStatus(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	boolean orderWinFundUpdateWinsStatus(GameContext ctx,Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException;
	/** 
	* @Title: orderWinFreeze 
	* @Description: 中奖表冻结中奖订单
	* @param orderWins
	* @return
	* @throws GameRiskException
	*/
	boolean orderWinFreeze(List<GameOrderWin> orderWins)  throws GameRiskException;

	/** 
	* @Title: getGameOrderWinByOrderId 
	* @Description: 通过orderID查询中奖订单，无则返回null
	* @param orderId
	* @return
	*/
	GameOrderWin getGameOrderWinByOrderId(Long orderId);
	
	/** 
	* @Title: getGameOrderWins 
	* @Description: 获取这一期中奖订单
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	List<GameOrderWin> getGameOrderWins(Long lotteryId, Long issueCode);
	
	TORiskDTO packageFundTORiskDTO(GameOrderWin orderWin);
	
	TORiskDTO packageCancelTORiskDTO(GameOrderWin orderWin);
	
	TORiskDTO packageFundTORiskDTO(Long orderId);
	
	TORiskDTO packageCancelTORiskDTO(Long orderId);
	
	void updateOrderWinsCancelStatus(Long orderId);
	
	void updateOrderWinsCancelStatus(Collection<Long> orderIds);
	
	
	//////////////////new///////////////
	//紧密下级业务操作不独立于上级业务进行，上级业务发起操作时传上级业务对象至下级 
	/**
	 * 派奖
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @return
	 * @throws Exception
	 */
	public int award(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;
	/**
	 * 撤销派奖
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @return
	 * @throws Exception
	 */
	public int unaward(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception;
}
