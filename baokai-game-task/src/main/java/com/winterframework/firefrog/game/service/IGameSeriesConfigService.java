package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;

/**
 * 
* @ClassName: IGameSeriesConfigService 
* @Description: 彩票配置接口
* @author Richard
* @date 2013-11-18 下午5:28:03 
*
 */
public interface IGameSeriesConfigService {

	/**
	 * 
	* @Title: getAllGameSeries 
	* @Description:根据条件获取彩种信息
	* @param lotteryId
	* @param status
	* @return
	* @throws Exception
	 */
	public List<GameSeries> getAllGameSeries(Long lotteryId, Integer status) throws Exception;

	/**
	 * 
	* @Title: queryGameSeriesConfigByLotteryId 
	* @Description: 查询运营参数信息 
	* @param lotteryId, status
	* @return GameSeriesConfigEntity
	* @throws Exception
	 */
	public GameSeriesConfigEntity queryGameSeriesConfigByLotteryId(Long lotteryId) throws Exception;
	
	/**
	 * 
	* @Title: gameSeriesChangeStatus 
	* @Description: 改變彩種販售狀態
	* @param 
	* @throws Exception
	 */
	public void gameSeriesChangeStatus() throws Exception;
	
	/**
	 * 獲取撤銷手續費
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public GameSeriesConfigEntity getSeriesConfigByLotteryId(Long lotteryId) throws Exception;
}
