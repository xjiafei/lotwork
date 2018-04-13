package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;

public interface IGameDiamondBettypeDao {
	
	public List<GameDiamondBettype> getDiamondBettypeByGroupCode(Long diamondLv);
}
