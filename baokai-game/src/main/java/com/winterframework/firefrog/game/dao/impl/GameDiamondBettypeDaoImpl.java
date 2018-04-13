package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameDiamondBettypeDao;
import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameDiamondBettypeDaoImpl")
public class GameDiamondBettypeDaoImpl extends BaseIbatis3Dao<GameDiamondBettype> implements IGameDiamondBettypeDao{

	@Override
	public List<GameDiamondBettype> getDiamondBettypeByGroupCode(Long diamondLv) {
		return	sqlSessionTemplate.selectList("getDiamondBettypeByGroupCode", diamondLv);
	}
}
