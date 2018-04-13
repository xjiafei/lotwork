/**   
* @Title: GameRiskConfigServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-21 下午5:34:38 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

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
	private IGameRiskConfigDao gameRiskConfigDao;
 
	@Override
	public GameRiskConfig getGameRiskConfig(Long lotteryId) throws Exception {
		GameRiskConfig config = this.gameRiskConfigDao.queryGameRiskConfig( lotteryId); 
		return config;
	}
 

}
