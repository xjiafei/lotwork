package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.entity.GameMmcTask;

public interface IGameMmcTaskDao {

	public int insertUpdate(GameMmcTask task);

	public List<GameMmcTask> queryBeforeNowTaskList(GameMmcTask request);

	public int update(GameMmcTask task);

}
