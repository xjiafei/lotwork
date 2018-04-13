package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;

public interface IGameDiamondBettypeService {
	
	public List<GameDiamondBettype> getDiamondBettypeByGroupCode(Long diamondLv);
}
