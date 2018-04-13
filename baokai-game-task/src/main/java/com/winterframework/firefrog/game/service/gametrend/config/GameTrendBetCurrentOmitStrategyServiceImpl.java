package com.winterframework.firefrog.game.service.gametrend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendBetStrategyService;

@Service("gameTrendBetCurrentOmitStrategyServiceImpl")
public class GameTrendBetCurrentOmitStrategyServiceImpl implements IGameTrendBetStrategyService {
	private static final Logger log = LoggerFactory.getLogger(GameTrendBetCurrentOmitStrategyServiceImpl.class);
	
	@Override
	public String getBetOmit(GameTrendJbyl trendJbyl,String lastValue, String curValue) { 
		return curValue;
	}
}
