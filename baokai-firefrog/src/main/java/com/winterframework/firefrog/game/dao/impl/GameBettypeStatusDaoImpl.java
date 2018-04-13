package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


@Repository("gameBettypeStatusDaoImpl")
public class GameBettypeStatusDaoImpl extends BaseIbatis3Dao<GameBettypeStatus> implements IGameBettypeStatusDao {
	private static final Logger log = LoggerFactory.getLogger(GameBettypeStatusDaoImpl.class);
	
	
	public List<String> getSuper2000BetTypeCode(){
		log.info("-----------------gameBettypeStatusDaoImpl---------------");
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getSuper2000BetTypeCode"));
	}
	
	public List<String> getSuper2000FOURLvlBtc(){
		log.info("-----------------getSuper2000FOURLvlBtc---------------");
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getSuper2000FOURLvlBtc"));
	}
	
	
	public Long getSuper2000TotalBets(Long userId, List<String> betTypeCodes,ActivityConfig actCfg,List<Integer> lotsList){
		log.info("-----------------getSuper2000TotalBets---------------");
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("betTypeCodes", betTypeCodes);
		map.put("startTime", actCfg.getBeginTime());
		map.put("endTime", actCfg.getEndTime());
		map.put("lotsList", lotsList);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getSuper2000TotalBets"),map);
	}
	
	public Long getTotalBets(Long userId, ActivityConfig actCfg,List<Integer> lotsList){
		log.info("-----------------getTotalBets---------------");
//		Map<String, Object> map =new HashMap<String, Object>();
//		map.put("userId", userId);
//		map.put("startTime", actCfg.getBeginTime());
//		map.put("endTime", actCfg.getEndTime());
//		map.put("lotsList", lotsList);
//		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getTotalBets"),map);
		return getTotalBetsByInterval(userId, actCfg.getBeginTime(), actCfg.getEndTime(), lotsList);
	}
	
	public Long getTotalBetsByInterval(Long userId, Date startTime, Date endTime, List<Integer> lotsList){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("lotsList", lotsList);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getTotalBets"),map);
	}
	
}
