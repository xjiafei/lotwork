package com.winterframework.firefrog.game.fund.service;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.TORiskDTO;

public interface IGameRiskService {
	/**
	 * 风控审核中心处理游戏资金操作接口
	 * @param request
	 * @throws Exception
	 */
	public void integration(List<TORiskDTO> riskDtoList) throws Exception; 
}
