/**   
* @Title: GameControlEventDaoImpl.java 
* @Package com.winterframework.firefrog.game.dao.impl 
* @Description: 后台主控任务类
* @author 你的名字   
* @date 2013-11-18 下午2:24:28 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameControlEventDaoImpl")
public class GameControlEventDaoImpl extends BaseIbatis3Dao<GameControlEvent> implements IGameControlEventDao {

	@Override
	public void addControlEvent(GameControlEvent event) throws Exception {
		this.insert(event);
	}

	@Override
	public List<GameControlEvent> getAllUnExcuteGameControlEvents(Long lotteryId) {
		return this.sqlSessionTemplate.selectList("getAllUnExcuteGameControlEvents", lotteryId);
	}
	
	public List<GameControlEvent> getTrendUnExcuteGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("isDependent", isDependent);
		map.put("realLotteryId", realLotteryId);
		return this.sqlSessionTemplate.selectList("getTrendUnExcuteGameControlEvents", map);
	}
	@Override
	public List<GameControlEvent> getIssueEndUnExcuteGameControlEvents(
			Long lotteryId, Integer isDependent, Long realLotteryId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("isDependent", isDependent);
		map.put("realLotteryId", realLotteryId);
		return this.sqlSessionTemplate.selectList("getIssueEndUnExecGameControlEvents", map); 
	}
	
 
	@Override
	public GameControlEvent getSameEventUnexecByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId); 
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getSameEventUnexecByLotteryIdAndIssueCode", map);
	}
	@Override
	public GameControlEvent getSameEventUnexecByLotteryIdAndIssueCodeTrend(
			Long lotteryId, Long issueCode, Long realLotteryId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("realLotteryId", realLotteryId);
		return this.sqlSessionTemplate.selectOne("getSameEventUnexecByLotteryIdAndIssueCodeTrend", map);
	}
	@Override
	public GameControlEvent getUnexecByLotteryIdAndIssueCodeAndEventType(
			Long lotteryId, Long issueCode,Long realLotteryId,Integer eventType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("realLotteryId", realLotteryId);
		map.put("eventType", eventType);
		return this.sqlSessionTemplate.selectOne("getUnexecByLotteryIdAndIssueCodeAndEventType", map);
	}
	@Override
	public List<GameControlEvent> getReDoGameControlEvents(Long lotteryId ,Date nextDoTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("nextDoTime", nextDoTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getReDoGameControlEvents"), map);
	}
	@Override
	public List<GameControlEvent> getReDoGameControlEvents(Long lotteryId,
			Integer isDependent, Long realLotteryId,Date nextDoTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("isDependent", isDependent);
		map.put("realLotteryId", realLotteryId);
		map.put("nextDoTime", nextDoTime);
		return this.sqlSessionTemplate.selectList(getQueryPath("getGameControlEventsAsync"), map);
	}
	
	@Override
	public void updateTaskStatusDone(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.update("updateTaskStatusDone", map);

	}

	@Override
	public void updateTaskStatusDoing(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.update("updateTaskStatusDoing", map);

	}

	@Override
	public void updateTaskStatusFail(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.update("updateTaskStatusFail", map);
	}
	
	@Override
	public void updateTaskStatusUndo(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.update("updateTaskStatusUndo", map);
	}
	/** 
	* @Title: updateTrendTaskStatusNotCare 
	* @Description: 重开或补开任务执行时，忽略掉该彩种后续奖期的走势图任务
	* @param gc
	*/
	public void updateTrendTaskStatusNotCare(GameControlEvent gc){
		Map<String, Object> map = new HashMap<String, Object>();
		//走势图彩种号存在endIssueCode字段里 
		map.put("lotteryId", gc.getEndIssueCode());
		map.put("issueCode", gc.getStartIssueCode());
		map.put("updateTime", new Date());
		this.sqlSessionTemplate.update("updateTrendTaskStatusNotCare",map);
	}
	
	@Override
	public Integer checkTreadTaskExisted(Long lotteryId,Long issueCode) throws Exception { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		Integer count=this.sqlSessionTemplate.selectOne("checkTreadTaskExisted",map);
		return count==null?0:1;
	}

	@Override
	public List<GameControlEvent> getUnExcuteControlEvents(Long lotteryId,
			EventType eventType) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(lotteryId!=null){
			map.put("lotteryId", lotteryId);
		}
		map.put("eventType", eventType.getValue());
		return this.sqlSessionTemplate.selectList("getUnExecEventsByEventTypeAndLotteryId", map); 
	}

	@Override
	public List<GameControlEvent> getInitExcuteControlEvents(Long lotteryId,
			EventType eventType) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(lotteryId!=null){
			map.put("lotteryId", lotteryId);
		}
		map.put("eventType", eventType.getValue());
		return this.sqlSessionTemplate.selectList("getInitExcuteEventsByEventTypeAndLotteryId", map); 
	}
}
