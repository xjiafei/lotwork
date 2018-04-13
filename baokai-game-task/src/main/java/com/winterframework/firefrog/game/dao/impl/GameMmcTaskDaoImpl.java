package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameMmcTaskDao;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameMmcTaskDaoImpl")
public class GameMmcTaskDaoImpl extends BaseIbatis3Dao<GameMmcTask>
		implements IGameMmcTaskDao {

	@Override
	public int insertUpdate(GameMmcTask task) {
		return sqlSessionTemplate.insert(this.getQueryPath("insertUpdate"), task);
	}

	@Override
	public List<GameMmcTask> queryBeforeNowTaskList(GameMmcTask request) {
		return sqlSessionTemplate.selectList("queryBeforeNowTaskList",
				request);
	}

	@Override
	public int update(GameMmcTask task) {
		return sqlSessionTemplate.update(this.getQueryPath("update"), task);
	}

}
