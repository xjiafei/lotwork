package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameControlEventDaoImpl")
public class GameControlEventDaoImpl extends BaseIbatis3Dao<GameControlEvent> implements IGameControlEventDao {

	@Override
	public void addControlEvent(GameControlEvent event) throws Exception {
		this.insert(event);
	}

	@Override
	public GameControlEvent queryEventByCondition(Long lotteryid, Long startIssueCode, Long endIssueCode)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("startIssueCode", startIssueCode);
		map.put("endIssueCode", endIssueCode);
		return this.sqlSessionTemplate.selectOne("queryEventByCondition", map);
	}

	
	@Override
	public void retryByLotteryIssueCode(Long lotteryid, Long issueCode)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
	
		this.sqlSessionTemplate.update("retryByLotteryIssueCode", map);
	}
	@Override
	public int changeStatus(Long lotteryId, Long startIssueCode,
			Long eventType, Long realLotteryId, Integer fromStatus,
			Integer toStatus, Date curDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startIssueCode", startIssueCode);
		map.put("eventType", eventType);
		map.put("realLotteryId", realLotteryId);
		map.put("fromStatus", fromStatus);
		map.put("toStatus", toStatus);
		map.put("curDate", curDate);
	
		return this.sqlSessionTemplate.update(getQueryPath("changeStatus"), map);
	}
	@Override
	public GameControlEvent getUnexecByLotteryIdAndIssueCodeAndEventType(
			Long lotteryId, Long issueCode,Long realLotteryId, Integer eventType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("realLotteryId", realLotteryId);
		map.put("eventType", eventType);
		return this.sqlSessionTemplate.selectOne("getUnexecByLotteryIdAndIssueCodeAndEventType", map);
	}
}
