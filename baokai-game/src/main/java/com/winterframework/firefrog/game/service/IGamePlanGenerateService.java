package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameIssueEntity;

public interface IGamePlanGenerateService {

	/**
	 * 
	* @Title: gamePlanGenerate 
	* @Description: 各彩种实现追号计划接口，每个彩种如果要自动执行追号计划，需要实现这个接口
	* @param gameIssue
	* @throws Exception
	 */
	public void gamePlanGenerate(GameIssueEntity gameIssue) throws Exception;
}
