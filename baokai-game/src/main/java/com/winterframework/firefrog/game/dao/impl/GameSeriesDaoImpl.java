package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameSeriesDaoImpl")
public class GameSeriesDaoImpl extends BaseIbatis3Dao<GameSeries> implements IGameSeriesDao {

	@Override
	public Float getMiniLotteryProfitByLotteryId(Long lotteryId) throws Exception {

		Number num = (Number) sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.game.dao.vo.GameSeries.getMiniLotteryProfitByLotteryId", lotteryId);

		return num.floatValue();
	}

	@Override
	public void addGameSeries(long lotteryid) {

	}

	@Override
	public void removeGameSeries(long lotteryid) {

	}

	@Override
	public void updateGameSeries(GameSeries gameSeries) {
		this.update(gameSeries);
	}

	@Override
	public String getLotteryHelpDes(long lotteryid) {
		return this.sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.game.dao.vo.GameSeries.getLotteryHelpDesByLotteryId", lotteryid);
	}

	@Override
	public Integer getLotterySellingStatus(long lotteryid) {
		return this.sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.game.dao.vo.GameSeries.getLotterySellingStatus", lotteryid);
	}

	@Override
	public GameSeries getByLotteyId(Long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getByLotteryId",
				lotteryid);
	}

	@Override
	public void updateForPublish(Long lotteryid, int status, Date date,Date takeOffTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("date", date);
		map.put("lotteryid", lotteryid);
		map.put("takeOffTime", takeOffTime);

		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeries.updateForPublish", map);
	}

	@Override
	public String getLotterySeriesNameByCode(Integer lotterySeriesCode) {
		return this.sqlSessionTemplate.selectOne("getLotterySeriesNameByCode", lotterySeriesCode);
	}

	@Override
	public List<GameSeries> getLotterySeriesByLotteryidAndStatus(Long lotteryid, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("lotteryid", lotteryid);

		return this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.game.dao.vo.GameSeries.getLotterySeriesByLotteryidAndStatus", map);
	}

	public List<GameSeries> queryGameSeriesByLotterySeriesCode(List<Long> lscList) {

		return this.sqlSessionTemplate.selectList("queryGameSeriesByLotterySeriesCode", lscList);
	}

	@Override
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Long lotterySeriesCode) {

		List<Long> lscList = new ArrayList<Long>();

		lscList.add(lotterySeriesCode);

		return queryGameSeriesByLotterySeriesCode(lscList);
	}

	@Override
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Set<Long> lotterySeriesCodes) {

		List<Long> lscList = new ArrayList<Long>();

		for (Long codes : lotterySeriesCodes) {
			lscList.add(codes);
		}
		return this.sqlSessionTemplate.selectList("queryGameSeriesByLotterySeries", lscList);
	}

	/**
	* Title: getMaxGameIssue
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameSeriesDao#getMaxGameIssue(java.lang.Long) 
	*/
	@Override
	public Long getMaxGameIssue(Long lotteryId) throws Exception {
		Long num = (Long) sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.game.dao.vo.GameSeries.getMaxGameIssue", lotteryId);
		return num;
	}

	@Override
	public void updateGameSeriesChangeStatus(Long lotteryId, int index, int status) throws Exception {
		GameSeries gameSeries = this.getByLotteyId(lotteryId==99110l?99109l:lotteryId);
		Long changeStatus = gameSeries.getChangeStatus();
		StringBuffer changeStatusStr = new StringBuffer("11111111");
		if (changeStatus != null) {
			changeStatusStr = new StringBuffer(String.valueOf(gameSeries.getChangeStatus()));
		}
		String s = status + "";
		changeStatusStr.setCharAt(index, s.charAt(0));
		gameSeries.setChangeStatus(Long.valueOf(changeStatusStr.toString()));

		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeries.updateChangeStatus", gameSeries);
	}

	/**
	* Title: updateLastModifyDate
	* Description:
	* @param lotteryId
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameSeriesDao#updateLastModifyDate(java.lang.Long) 
	*/
	@Override
	public void updateLastModifyDate(Long lotteryId){
		GameSeries gameSeries = this.getByLotteyId(lotteryId);
		gameSeries.setGmtModified(new Date());
		gameSeries.setUpdateTime(new Date());
		this.update(gameSeries);
	}
	@Override
	public List<GameSeries> getGameSeries() throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("queryGameSeriesByLotterySeries"));
	}

}
