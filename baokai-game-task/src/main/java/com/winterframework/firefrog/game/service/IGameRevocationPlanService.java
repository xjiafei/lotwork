package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/** 
* @ClassName: IGameRevocationPlanService 
* @Description: 撤销后续追号Service 
* @author Alan
* @date 2013-11-19 下午3:56:37 
*  
*/
public interface IGameRevocationPlanService {

	/**
	 * 
	* @Title: revocationFollowPlan 
	* @Description: 撤销后续追号 
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @throws Exception
	 */
	public void revocationFollowPlan(GameControlEvent event) throws Exception;

}
