package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;

 
/**
 * 走势图投注页服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
public interface IGameTrendBetService { 
	////////////////////////行为----begin////////////////////////// 
	int save(GameContext ctx,GameTrendBet trendBet) throws Exception;
	int save(GameContext ctx,List<GameTrendBet> trendBetList) throws Exception;
	int remove(GameContext ctx,Long id) throws Exception;
	////////////////////////行为----end//////////////////////////  	
		
	////////////////////////业务服务----begin//////////////////////////  
	/**
	 * 获取最近的记录（根据彩种、玩法）
	 * @param ctx
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @param type
	 * @return
	 * @throws Exception
	 */
	GameTrendBet getByLotteryIdAndBetTypeAndType(GameContext ctx,Long lotteryId,Integer groupCode,Integer setCode,Integer methodCode,Integer type) throws Exception;
	List<GameTrendBet> getByLotteryId(GameContext ctx,Long lotteryId) throws Exception;
	////////////////////////业务服务----end////////////////////////// 
}
