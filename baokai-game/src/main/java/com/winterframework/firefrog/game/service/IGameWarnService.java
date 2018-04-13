package com.winterframework.firefrog.game.service;

import java.util.Set;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;

public interface IGameWarnService {
	
	void updateIssuseRedisCache(GameOrder order);
}
