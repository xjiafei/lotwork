package com.winterframework.firefrog.game.service.gametrend;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

public interface IGameTrendBetStrategyService {
	String SEPERATOR=",";
	String getBetOmit(GameTrendJbyl trendJbyl,String lastValue,String curValue) throws Exception;
}
