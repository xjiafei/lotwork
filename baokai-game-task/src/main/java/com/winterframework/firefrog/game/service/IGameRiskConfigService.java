package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.GameRiskConfig;
 
/**
 * 风险审核配置信息接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月16日
 */
public interface IGameRiskConfigService {
	
	 
	/**
	 * 获取风险审核配置信息
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public GameRiskConfig getGameRiskConfig(Long lotteryId) throws Exception; 

}
