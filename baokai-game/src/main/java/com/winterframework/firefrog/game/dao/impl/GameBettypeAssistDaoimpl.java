package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameBettypeAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameBettypeAssistDaoimpl")
public class GameBettypeAssistDaoimpl extends BaseIbatis3Dao<GameBettypeAssist> implements IGameBettypeAssistDao {

	@Override
	public List<GameBettypeAssist> getBettypeAssistListByTypeIds(List<Long> betTypeStatusIds) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getBettypeAssistListByTypeIds"), betTypeStatusIds);
	}
}
