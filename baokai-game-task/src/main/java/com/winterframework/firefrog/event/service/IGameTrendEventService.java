package com.winterframework.firefrog.event.service;

import com.winterframework.firefrog.game.dao.vo.GameTrendTask;


public interface IGameTrendEventService {
	/**
	 * 任务处理
	 * @param trendTask
	 * @throws Exception
	 */
	void doProcess(GameTrendTask trendTask) throws Exception;
}
