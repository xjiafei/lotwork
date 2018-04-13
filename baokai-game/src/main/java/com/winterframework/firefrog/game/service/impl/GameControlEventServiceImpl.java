package com.winterframework.firefrog.game.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.service.IGameControlEventService;

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
	public int changeStatus(Long lotteryId, Long startIssueCode,
			Long eventType, Long realLotteryId, Integer fromStatus,Integer toStatus) { 
		return gameControlEventDao.changeStatus(lotteryId, startIssueCode, eventType, realLotteryId, fromStatus, toStatus, DateUtils.currentDate());
	}
	@Override
	public void retryDraw(Long lotteryId, Long issueCode) throws Exception {
		changeStatus(lotteryId, issueCode, new Long(GameControlEvent.EventType.DRAW.getValue()), null, GameControlEvent.Status.FAIL.getValue(), GameControlEvent.Status.INIT.getValue());
		changeStatus(GameControlEvent.AsyncType.FUND.getValue(), issueCode, new Long(GameControlEvent.EventType.ORDER_FUND.getValue()), lotteryId, GameControlEvent.Status.FAIL.getValue(), GameControlEvent.Status.INIT.getValue());
	}
	@Override
	public boolean addPlanEvent(Long lotteryId, Long issueCode, Long planId) throws Exception {
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(GameControlEvent.AsyncType.PLAN.getValue());
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(lotteryId);
		event.setEnentType(new Long(GameControlEvent.EventType.PLAN.getValue()));
		event.setParams("lotteryId:"+lotteryId+";issueCode:"+issueCode+";planId:"+planId);
		event.setMessage("追号任务");
		event.setSaleStartTime(DateUtils.currentDate());
		event.setSaleEndTime(DateUtils.currentDate());
		event.setStatus(new Long(GameControlEvent.Status.INIT.getValue())); 
		event.setCreateTime(DateUtils.currentDate());
		save(event);
		return true;
	};
	
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
}
