package com.winterframework.firefrog.game.mmctask;

import com.winterframework.firefrog.game.dao.entity.GameMmcTask;

public interface IGameMmcTaskHandler {
	
	public void execute(GameMmcTask task) throws Exception;
	
}
