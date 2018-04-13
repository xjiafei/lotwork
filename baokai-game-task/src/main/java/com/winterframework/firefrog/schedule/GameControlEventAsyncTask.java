package com.winterframework.firefrog.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
 
//@Component("gameControlEventTask")
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
/**
 * 异步任务 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年1月29日
 */
public class GameControlEventAsyncTask extends GameControlEventTask{ 

	private Logger log = LoggerFactory.getLogger(GameControlEventAsyncTask.class); 

	@Override
	protected List<GameControlEvent> getGameControlEvents(Long lotteryId,
			Integer isDependent, Long realLotteryId) { 
		List<GameControlEvent> gameControlEvents=gameControlEventService.getAllUnExcuteGameControlEventsTrend(lotteryId,isDependent,realLotteryId);
		if(gameControlEvents==null){
			gameControlEvents=new ArrayList<GameControlEvent>();
		}
		/*//重做2次
		try {
			gameControlEvents.addAll(gameControlEventService.getReDoGameControlEvents(lotteryId,isDependent,realLotteryId));	
		} catch (Exception e) {
			log.error("get redo event fail" , e);
		}*/
		return gameControlEvents;
	}
	@Override
	protected void saveGameIssueRedoLog(GameControlEvent gc) {
		return;
	}
	@Override
	protected void updateGameWarnIssueHandled(GameControlEvent gc)
			throws Exception {
		return;
	}
	@Override
	protected void updateGameWarnIssueExcep(GameControlEvent gc) {
		return;
	}
	 
}
