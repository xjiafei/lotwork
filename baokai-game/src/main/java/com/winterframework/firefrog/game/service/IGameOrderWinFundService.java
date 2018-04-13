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

	
	boolean orderWinFundAndUpdateWinStatus(GameOrder order) throws GameRiskException;
	
	public boolean orderWinFund(GameOrder order, GameOrderWin orderWin) throws GameRiskException;
	
	TORiskDTO packageFundTORiskDTO(GameOrderWin orderWin);
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
