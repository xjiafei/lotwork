package com.winterframework.firefrog.game.dao.impl;

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
		
		Number num = (Number)sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getMiniLotteryProfitByLotteryId", lotteryId);
		
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
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getLotteryHelpDesByLotteryId", lotteryid);
	}

	@Override
	public Integer getLotterySellingStatus(long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getLotterySellingStatus", lotteryid);
	}

	@Override
	public GameSeries getByLotteyId(Long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getByLotteryId", lotteryid);
	}

	@Override
	public void updateForPublish(Long lotteryid, int status, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("date", date);
		map.put("lotteryid", lotteryid);
		
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
		
		return this.sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameSeries.getLotterySeriesByLotteryidAndStatus", map);
	}
	
	@Override
	public String getLotteryNameByLotteryid(Long lotteryid) {		
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeries.getLotteryNameByLotteryid", lotteryid);
	}
	
	public List<GameSeries> queryGameSeriesByLotterySeriesCode(String lotterySeriesCodes){
		
		return this.sqlSessionTemplate.selectList("queryGameSeriesByLotterySeriesCode", lotterySeriesCodes);
	}

	@Override
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Long lotterySeriesCode) {
		
		return queryGameSeriesByLotterySeriesCode(String.valueOf(lotterySeriesCode));
	}

	@Override
	public List<GameSeries> getGameSeriesByLotterySeriesCode(Set<Long> lotterySeriesCodes) {
		StringBuffer buffer = new StringBuffer();
		for (Long codes : lotterySeriesCodes) {
			buffer.append(codes);
			buffer.append(",");
		}
		String str = "";
		if(buffer.toString().endsWith(",")){
			str = buffer.substring(0, buffer.lastIndexOf(","));
		}
		return queryGameSeriesByLotterySeriesCode(str);
	}

}
