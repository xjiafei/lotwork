package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesConfig;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameSeriesConfigDao extends BaseDao<GameSeriesConfig> {

	/**
	 * 
	* @Title: getGameSeriesConfigByLotteryId 
	* @Description:根据LotteryId获取GameSeriesConfig信息 
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) throws Exception;

	/**
	* 
	* @Title: getAllGameSeries 
	* @Description:根据条件获取彩种信息
	* @param status
	* @return
	* @throws Exception
	*/
	public List<GameSeries> getAllGameSeries(Integer status) throws Exception;

}
