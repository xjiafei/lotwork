package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.web.dto.TORiskRequest;

public interface IGameRiskService {
	/**
	 * 风控审核中心处理游戏资金操作接口
	 * @param request
	 * @throws Exception
	 */
	public void integration(TORiskRequest request) throws Exception;
	public void integrationNew(TORiskRequest request) throws Exception;
}
