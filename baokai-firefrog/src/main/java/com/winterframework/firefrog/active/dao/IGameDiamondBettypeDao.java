package com.winterframework.firefrog.active.dao;

import java.util.List;

import com.winterframework.firefrog.active.dao.vo.GameDiamondBettype;


public interface IGameDiamondBettypeDao {
	
	public List<GameDiamondBettype> getDiamondBettypeByGroupCode(Long diamondLv);
}
