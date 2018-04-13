package com.winterframework.firefrog.game.service.gametrend.config;

import com.winterframework.firefrog.game.service.gametrend.IGameTrendBetStrategyService;
import java.util.HashMap;

public class GameTrendBetStrategyFactory extends HashMap<Integer, IGameTrendBetStrategyService> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982262332681225915L;

	public IGameTrendBetStrategyService getInstance(Integer type){
		return this.get(type.toString());
	}	
}
