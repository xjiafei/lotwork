package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameSeriesDao extends BaseDao<GameSeries> {

	/**
	* @Title: getLotterySellingStatus 
	* @Description: 根据彩种查询销售状态
	 */
	public GameSeries getByLotteyId(Long lotteryid);

	/** 
	* @Title: getAllValidLottery 
	* @Description:获取有效彩种列表 
	* @param status
	* @return
	*/
	public List<GameSeries> getAllValidLottery(Integer status);
}
