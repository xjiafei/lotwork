package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameRiskConfig;

/** 
* @ClassName: IGameRiskConfigService 
* @Description: 审核参数Service
* @author Alan
* @date 2014-2-21 下午5:31:55 
*  
*/
public interface IGameRiskConfigService {
	
	/**
	 * 
	* @Title: selectGameRiskConfig 
	* @Description: 查询审核参数信息
	* @return GameRiskConfig
	* @throws Exception
	 */
	public GameRiskConfig selectGameRiskConfig() throws Exception;
	
	/**
	 * 
	* @Title: updateGameRiskConfig 
	* @Description: 修改审核参数信息
	* @param grc
	* @throws Exception
	 */
	public void updateGameRiskConfig(GameRiskConfig grc) throws Exception;

}
