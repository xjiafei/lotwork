package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;

/** 
* @ClassName: IGameLotteryService 
* @Description: 彩种Service接口 
* @author Denny 
* @date 2013-9-30 上午11:43:33 
*  
*/
public interface IGameLotteryService {

	/**
	* @Title: querySlipsByOrderId
	* @Description: 根据订单ID查询注单列表
	*/
	public List<GameSeries> queryLotteryList(Long lotteryid, Integer status);
}
