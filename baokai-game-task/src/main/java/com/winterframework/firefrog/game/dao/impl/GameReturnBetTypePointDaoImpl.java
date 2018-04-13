package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameReturnBetTypePointDao;
import com.winterframework.firefrog.game.dao.vo.GameRetBetTypePoint;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


@Repository("gameReturnBetTypePointDaoImpl")
public class GameReturnBetTypePointDaoImpl extends BaseIbatis3Dao<GameRetBetTypePoint> implements IGameReturnBetTypePointDao	 {

	@Override
	public void saveGameRetBetTypePoint(GameRetBetTypePoint gameRetBetTypePoint) throws Exception {		
		insert(gameRetBetTypePoint);
	}

}
