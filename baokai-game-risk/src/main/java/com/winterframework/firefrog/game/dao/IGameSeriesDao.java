package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameSeriesDao extends BaseDao<GameSeries> {
	String getLotteryNameByLotteryid(Long lotteryid) ;
	/**
	 * 
	* @Title: getMiniLotteryProfitByLotteryId 
	* @Description: 获取公司最小留水 
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public Float getMiniLotteryProfitByLotteryId(Long lotteryId) throws Exception;
	
	/**
	* @Title: addGameSeries 
	* @Description: 添加销售状态
	 */
	public void addGameSeries(long lotteryid);
	
	/**
	* @Title: removeGameSeries 
	* @Description: 删除销售状态
	 */
	public void removeGameSeries(long lotteryid);
	
	/**
	* @Title: updateGameSeries 
	* @Description: 修改销售状态
	 */
	public void updateGameSeries(GameSeries gameSeries);
	
	/**
	* @Title: getLotteryHelpDes 
	* @Description: 查询彩种开奖周期描述
	 */
	public String getLotteryHelpDes(long lotteryid);

	/**
	* @Title: getLotterySellingStatus 
	* @Description: 查询彩种销售状态
	 */
	public Integer getLotterySellingStatus(long lotteryid);

	/**
	* @Title: getLotterySellingStatus 
	* @Description: 根据彩种查询销售状态
	 */
	public GameSeries getByLotteyId(Long lotteryid);

	/** 
	* @Title: updateForPublish 
	* @Description: 发布后，更新主表销售状态 
	* @throws 
	*/
	public void updateForPublish(Long lotteryid, int status, Date date);

	/** 
	* @Title: getLotterySeriesNameByCode 
	* @Description: 根据彩系编码查询彩系名称 
	*/
	public String getLotterySeriesNameByCode(Integer lotterySeriesCode);
	
	/** 
	* @Title: getLotterySeriesByLotteryidAndStatus 
	* @Description: 彩种列表查询 
	*/
	public List<GameSeries> getLotterySeriesByLotteryidAndStatus(Long lotteryid, Integer status);
	
	/**
	 * 
	* @Title: getGameSeriesByLotterySeriesCode 
	* @Description: 根据彩系Code找彩种信息。
	* @param LotterySeriesCode
	* @return
	 */
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Long lotterySeriesCode);
	
	/**
	 * 
	* @Title: getGameSeriesByLotterySeriesCode 
	* @Description: 根据彩系Code找彩种信息。
	* @param LotterySeriesCode
	* @return
	 */
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Set<Long> lotterySeriesCodes);
} 
