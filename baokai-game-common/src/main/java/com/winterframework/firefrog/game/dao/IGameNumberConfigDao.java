package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

public interface IGameNumberConfigDao {

	/**
	 * 根據現在日期取得該年度的生肖對應
	 * @return
	 */
	List<GameNumberConfig> getByEffDate();
	
	public List<GameNumberConfig> getByYearLaterDate(Date date);
	
}

