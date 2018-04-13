package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;

 
/**
 * 开奖号码走势图投注页信息DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
public interface IGameTrendBetService {
   
	/**
	 * 获取最近的记录（根据彩种、玩法）
	 * @param ctx
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @return
	 * @throws Exception
	 */
	GameTrendBet getByLotteryIdAndBetTypeAndType(GameContext ctx,Long lotteryId,Integer groupCode,Integer setCode,Integer methodCode,Integer type) throws Exception;
}
