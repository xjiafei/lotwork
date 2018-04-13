package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.entity.GameRiskConfig;

/** 
* @ClassName: IGameRiskConfigDao 
* @Description: 审核参数操作Dao 
* @author Alan
* @date 2014-2-21 下午5:22:34 
*  
*/
public interface IGameRiskConfigDao {
	
	/**
	 * 
	* @Title: queryGameRiskConfig 
	* @Description: 查询审核参数信息 
	* @return GameRiskConfig
	* @throws Exception
	 */
	public GameRiskConfig queryGameRiskConfig(Long lotteryId) throws Exception;
	
	/**
	 * 
	* @Title: insertGameRiskConfig 
	* @Description: 添加审核参数信息
	* @param config
	* @throws Exception
	 */
	public void insertGameRiskConfig(GameRiskConfig config) throws Exception;
	
	/**
	 * 
	* @Title: updateGameRiskConfig 
	* @Description: 更新审核参数信息
	* @param config
	* @throws Exception
	 */
	public void updateGameRiskConfig(GameRiskConfig config) throws Exception;
	
}
