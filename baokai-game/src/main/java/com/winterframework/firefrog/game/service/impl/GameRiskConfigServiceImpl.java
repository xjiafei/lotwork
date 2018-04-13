/**   
* @Title: GameRiskConfigServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-21 下午5:34:38 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.service.IGameRiskConfigService;

@Service("gameRiskConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameRiskConfigServiceImpl implements IGameRiskConfigService {
	
	@Resource(name = "gameRiskConfigDaoImpl")
	private IGameRiskConfigDao gameRiskConfigDaoImpl;

	/**
	* Title: selectGameRiskConfig
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameRiskConfigService#selectGameRiskConfig() 
	*/
	@Override
	public GameRiskConfig selectGameRiskConfig() throws Exception {
		GameRiskConfig config = gameRiskConfigDaoImpl.queryGameRiskConfig();
		
		//如果没有发现审核参数，则初始化数据
		if(config == null){
			config = new GameRiskConfig();
			
			config.setOrderwarnMaxwins(100000L);
			config.setOrderwarnWinsRatio(100000L);
			config.setOrderwarnContinuousWins(1L);
			config.setOrderwarnMaxslipWins(100000L);
			config.setOrderwarnSlipWinsratio(100000L);
			config.setCreateTime(new Date());
			
			gameRiskConfigDaoImpl.insertGameRiskConfig(config);
		}
		
		return config;
	}

	/**
	* Title: updateGameRiskConfig
	* Description:
	* @param grc
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameRiskConfigService#updateGameRiskConfig(com.winterframework.firefrog.game.entity.GameRiskConfig) 
	*/
	@Override
	public void updateGameRiskConfig(GameRiskConfig grc) throws Exception {
		GameRiskConfig config = gameRiskConfigDaoImpl.queryGameRiskConfig();
		
		config.setOrderwarnContinuousWins(grc.getOrderwarnContinuousWins());
		config.setOrderwarnMaxslipWins(grc.getOrderwarnMaxslipWins());
		config.setOrderwarnMaxwins(grc.getOrderwarnMaxwins());
		config.setOrderwarnSlipWinsratio(grc.getOrderwarnSlipWinsratio());
		config.setOrderwarnWinsRatio(grc.getOrderwarnWinsRatio());
		config.setUpdateTime(new Date());
		
		gameRiskConfigDaoImpl.updateGameRiskConfig(config);
	}

	public void setGameRiskConfigDaoImpl(IGameRiskConfigDao gameRiskConfigDaoImpl) {
		this.gameRiskConfigDaoImpl = gameRiskConfigDaoImpl;
	}

}
