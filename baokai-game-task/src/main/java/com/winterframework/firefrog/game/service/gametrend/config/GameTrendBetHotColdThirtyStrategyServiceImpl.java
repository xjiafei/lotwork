package com.winterframework.firefrog.game.service.gametrend.config;

import org.springframework.stereotype.Service;

@Service("gameTrendBetHotColdThirtyStrategyServiceImpl")
public class GameTrendBetHotColdThirtyStrategyServiceImpl extends AbstractGameTrendBetHotColdStrategyService {
	 @Override
	protected Integer getNum() { 
		return 30;
	}
}
