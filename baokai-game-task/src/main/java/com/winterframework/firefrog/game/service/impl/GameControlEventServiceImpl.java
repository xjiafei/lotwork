package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams.TrendEventTypeEnum;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: GameControlEventServiceImpl 
* @Description: 主控service层
* @author david 
* @date 2013-11-21 下午8:14:03 
*  
*/
@Service("gameControlEventServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameControlEventServiceImpl implements IGameControlEventService {

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Override
	public int save(GameControlEvent event) throws Exception {
		int retCount=0;
		if(event!=null){
			Date curDate=DateUtils.currentDate();
			if(event.getId()!=null){
				event.setUpdateTime(curDate);
				retCount=this.gameControlEventDao.update(event);
			}else{
				event.setCreateTime(curDate);
				retCount=this.gameControlEventDao.insert(event);
			}
		}
		return retCount;
	}
	@Override
	public List<GameControlEvent> getAllUnExcuteGameControlEvents(Long lotteryId) { 
		return gameControlEventDao.getAllUnExcuteGameControlEvents(lotteryId); 
	}
	@Override
	public List<GameControlEvent> getToExcuteControlEvents(Long lotteryId ) {
		List<GameControlEvent> gameControlEvents1 =  getAllUnExcuteGameControlEvents( lotteryId);
		List<GameControlEvent> gameControlEvents2 =  getAllUnExcuteGameControlEvents( lotteryId);
		if(gameControlEvents1 == null){
			gameControlEvents1 = new ArrayList<GameControlEvent>();
		}
		gameControlEvents1.addAll(gameControlEvents2);
		return gameControlEvents1;		
	}
	@Override
	public List<GameControlEvent> getReDoGameControlEvents(Long lotteryId ){
		return gameControlEventDao.getReDoGameControlEvents(lotteryId, new Date());
	}
	@Override
	public List<GameControlEvent> getReDoGameControlEvents(Long lotteryId,
			Integer isDependent, Long realLotteryId) {
		return gameControlEventDao.getReDoGameControlEvents(lotteryId,isDependent,realLotteryId,DateUtils.currentDate());
	}
	@Override
	public List<GameControlEvent> getAllUnExcuteGameControlEventsTrend(
			Long lotteryId, Integer isDependent, Long realLotteryId) { 
		return gameControlEventDao.getTrendUnExcuteGameControlEvents(lotteryId,isDependent,realLotteryId);
	}
	@Override
	public List<GameControlEvent> getAllUnExcuteGameControlEventsIssueEnd(
			Long lotteryId, Integer isDependent, Long realLotteryId) {
		return gameControlEventDao.getIssueEndUnExcuteGameControlEvents(lotteryId,isDependent,realLotteryId); 
	}
	@Override
	public GameControlEvent getSameEventUnexecByLotteryIdAndIssueCode(Long lotteryId,Long issueCode) {
		return gameControlEventDao.getSameEventUnexecByLotteryIdAndIssueCode(lotteryId,issueCode);
	}
	public GameControlEvent getSameEventUnexecByLotteryIdAndIssueCodeTrend(Long lotteryId, Long issueCode, Long realLotteryId) {
		return gameControlEventDao.getSameEventUnexecByLotteryIdAndIssueCodeTrend(lotteryId,issueCode,realLotteryId);
	}; 
	
	@Override
	public GameControlEvent getUnexecByLotteryIdAndIssueCodeAndTrendType(
			Long lotteryId, Long issueCode,Long realLotteryId,Integer eventType) {
		return gameControlEventDao.getUnexecByLotteryIdAndIssueCodeAndEventType(lotteryId,issueCode,realLotteryId,eventType);
	}
	@Override
	public void updateTaskStatusDone(Long id) {
		gameControlEventDao.updateTaskStatusDone(id);

	}

	@Override
	public void updateTaskStatusDoing(Long id) {
		gameControlEventDao.updateTaskStatusDoing(id);

	}

	@Override
	public void updateTaskStatusFail(Long id) {
		gameControlEventDao.updateTaskStatusFail(id);
		
	}
	
	@Override
	public void updateTaskStatusFail(GameControlEvent gc) {
		if(gc.getEnentType().intValue()==GameControlEvent.EventType.ORDER_FUND.getValue()
				&& gc.getRedoNumber()==null){ 
			gc.setStatus(0L);
			gc.setRedoNumber(999);
		}else{
			gc.setStatus(3L);
			if(gc.getMessage()!=null && gc.getMessage().length()>1600){
				gc.setMessage(gc.getMessage().substring(0, 1600));
			}
			
			if(gc.getRedoNumber() == null){
				gc.setRedoNumber(1);
			}else{
				gc.setRedoNumber(gc.getRedoNumber() + 1);
			}
			long date = new Date().getTime();
			if(gc.getRedoNumber().intValue() == 1){
				date += 10 * 1000;
			}else if(gc.getRedoNumber().intValue() == 2){
				date += 60 * 1000;
			}else if(gc.getRedoNumber().intValue() == 3){
				date += 100 * 1000;
			}else if(gc.getRedoNumber().intValue() == 4){
				date += 1800 * 1000;
			}
			gc.setNextDoTime(new Date(date));
		}
		gameControlEventDao.update(gc);
	}
	
	/** 
	* @Title: updateTrendTaskStatusNotCare 
	* @Description: 重开或补开任务执行时，忽略掉该彩种后续奖期的走势图任务
	* @param gc
	*/
	public void updateTrendTaskStatusNotCare(GameControlEvent gc){
		gameControlEventDao.updateTrendTaskStatusNotCare(gc);
	}
	@Override
	public boolean addCreateWinReportEvent(GameIssue issue) {
		GameControlEvent event=this.gameControlEventDao.getUnexecByLotteryIdAndIssueCodeAndEventType(GameControlEvent.AsyncType.WIN_REPORT.getValue(), issue.getIssueCode(),issue.getLotteryid(), GameControlEvent.EventType.WIN_REPORT.getValue());
		if(event==null){
			event = new GameControlEvent();
			event.setCreateTime(new Date());
			event.setStartIssueCode(issue.getIssueCode());
			event.setEndIssueCode(issue.getLotteryid());
			event.setEnentType(new Long(GameControlEvent.EventType.WIN_REPORT.getValue()));
			event.setLotteryid(GameControlEvent.AsyncType.WIN_REPORT.getValue());
			event.setMessage("生成奖期盈亏报表信息。");
			event.setSaleEndTime(issue.getSaleEndTime());
			event.setSaleStartTime(issue.getSaleStartTime());
			event.setStatus(0L);
			gameControlEventDao.insert(event);
		}
		return true;
	}
	@Override
	public boolean addGameTrendChartRegenerateEvent(GameIssue issue) {
		GameControlEvent event = new GameControlEvent();				
		event.setCreateTime(new Date()); 
		event.setEnentType(new Long(GameControlEvent.EventType.TREND.getValue()));
		event.setLotteryid(GameControlEvent.AsyncType.TREND.getValue());
		event.setMessage("生成走势图。");

		GameTrendEventParams params = new GameTrendEventParams();
		params.setLotteryId(issue.getLotteryid());
		params.setIssueCode(issue.getIssueCode());
		params.setType(TrendEventTypeEnum.ADD.getCode());
		params.setNumberRecord(issue.getNumberRecord());
		event.setParams(JsonMapper.nonEmptyMapper().toJson(params));

		event.setSaleEndTime(issue.getSaleEndTime());
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setStartIssueCode(issue.getIssueCode());
		event.setEndIssueCode(issue.getLotteryid());
		event.setStatus(0L);
		gameControlEventDao.insert(event);
		return true;
	}
	@Override
	public List<GameControlEvent> getToExcuteControlEvents(Long lotteryId,
			EventType eventType) {
		return gameControlEventDao.getUnExcuteControlEvents(lotteryId, eventType);
	}
	@Override
	public List<GameControlEvent> getInitExcuteControlEvents(Long lotteryId,
			EventType eventType) {
		return gameControlEventDao.getInitExcuteControlEvents(lotteryId, eventType);
	}
	@Override
	public void updateTaskStatusUndo(Long id) {
		gameControlEventDao.updateTaskStatusUndo(id);
	}
}
