package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;


 
/**
 * 奖期销售结束或者开始追号接口
 * @ClassName
 * @Description
 * @author ibm
 * 2015年1月16日
 */
public interface IGameIssueEndPlanService {  
	/**
	 * 奖期销售开始时追号
	 * @param ctx
	 * @param result
	 * @param lotteryId
	 * @param issueCode
	 * @param planId
	 * @throws Exception
	 */
	public void generatePlanWhenStart(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode) throws Exception;
	/**
	 * 奖期销售结束时追号
	 * @param ctx
	 * @param result
	 * @param lotteryId
	 * @param issueCode
	 * @param planId
	 * @throws Exception
	 */
	public void generatePlanWhenEnd(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode) throws Exception;
}
