package com.winterframework.firefrog.game.service.gametrend.config;

import org.springframework.stereotype.Service;

@Service("gameTrendBetHotColdSixtyStrategyServiceImpl")
public class GameTrendBetHotColdSixtyStrategyServiceImpl extends AbstractGameTrendBetHotColdStrategyService {
	 @Override
	protected Integer getNum() { 
		return 60;
	}
}
