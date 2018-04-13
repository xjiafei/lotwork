package com.winterframework.firefrog.game.service.gametrend.config;

import org.springframework.stereotype.Service;

@Service("gameTrendBetHotColdHundrendStrategyServiceImpl")
public class GameTrendBetHotColdHundrendStrategyServiceImpl  extends AbstractGameTrendBetHotColdStrategyService {
	@Override
	protected Integer getNum() { 
		return 100;
	}
}
