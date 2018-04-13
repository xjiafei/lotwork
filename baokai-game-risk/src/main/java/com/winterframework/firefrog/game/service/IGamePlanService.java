package com.winterframework.firefrog.game.service;

/** 
* @ClassName IGamePlanService 
* @Description 计划相关服务 
* @author  hugh
* @date 2014年5月30日 上午11:11:49 
*  
*/
public interface IGamePlanService{

	public void continueGamePlan(Long planId) throws Exception;

	public void pauseGamePlan(Long planId) throws Exception ;

}
