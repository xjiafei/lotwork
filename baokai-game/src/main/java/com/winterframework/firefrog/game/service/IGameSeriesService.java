package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;

public interface IGameSeriesService {

	public List<GameSeries> getAll();
	public List<GameSeries> getAllForSale();
	/**
	 * 获取在售的所有彩系列表
	 * @return
	 * @throws Exception
	 */
	public List<GameSeries> getAllSeriesForSale() throws Exception;
}
