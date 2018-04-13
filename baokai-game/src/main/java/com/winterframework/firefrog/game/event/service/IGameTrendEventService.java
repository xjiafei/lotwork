package com.winterframework.firefrog.game.event.service;

import com.winterframework.firefrog.game.dao.vo.GameTrendTask;


public interface IGameTrendEventService {
	/**
	 * 任务处理
	 * @param trendTask
	 * @throws Exception
	 */
	void doProcess(GameTrendTask trendTask) throws Exception;
}
