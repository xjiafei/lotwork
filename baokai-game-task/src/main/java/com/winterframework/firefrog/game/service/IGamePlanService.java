package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;


/**
 * 
* @ClassName: IGamePlanService 
* @Description: 追号计划 
* @author Richard
* @date 2013-11-18 下午4:57:44 
*
 */
public interface IGamePlanService {



	/**
	 * 
	* @Title: generateGamePlan 
	* @Description: 追号计划
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception;
	public void generateGamePlan(GameContext ctx,ProcessResult result,Long lotteryId, Long issueCode, Long planId) throws Exception;


	/**
	 * 
	* @Title: generateGamePlan 
	* @Description: 追号计划
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	public void generateGamePlan(ProcessResult result,Long lotteryId, Long issueCode) throws Exception;
}
