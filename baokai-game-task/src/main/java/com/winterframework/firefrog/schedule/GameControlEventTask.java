package com.winterframework.firefrog.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.event.service.IEventDispatcher;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.service.IGameWarnIssueService;

/**
 * @ClassName: GameControlEventTask
 * @Description: 主控调度任务
 * @author david
 * @date 2013-11-21 下午8:07:29
 * 
 */
//@Component("gameControlEventTask")
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameControlEventTask {

	public static String TASK_DOING = "true";// 彩种有任务正在执行标识
	public static String TASK_UNDO = "false";// 彩种没有任务正在执行表示

	private Logger log = LoggerFactory.getLogger(GameControlEventTask.class);

	//@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	//@Resource(name = "gameWarnIssueServiceImpl")
	private IGameWarnIssueService gameWarnIssueService;

	//@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssuelogService;

	//@Resource(name = "eventDispatcher")
	private IEventDispatcher eventDispatcher;

	private Long lotteryId;
	
	private Integer isDependent=0;
	private Long realLotteryId;

	//@Resource(name = "gameControlEventServiceImpl")
	protected IGameControlEventService gameControlEventService;

	public IGameControlEventService getGameControlEventService() {
		return gameControlEventService;
	}

	public void setGameControlEventService(IGameControlEventService gameControlEventService) {
		this.gameControlEventService = gameControlEventService;
	}

	public IGameIssueService getGameIssueService() {
		return gameIssueService;
	}

	public void setGameIssueService(IGameIssueService gameIssueService) {
		this.gameIssueService = gameIssueService;
	}

	public IGameWarnIssueService getGameWarnIssueService() {
		return gameWarnIssueService;
	}

	public void setGameWarnIssueService(IGameWarnIssueService gameWarnIssueService) {
		this.gameWarnIssueService = gameWarnIssueService;
	}

	public IGameWarnIssueLogService getGameWarnIssuelogService() {
		return gameWarnIssuelogService;
	}

	public void setGameWarnIssuelogService(IGameWarnIssueLogService gameWarnIssuelogService) {
		this.gameWarnIssuelogService = gameWarnIssuelogService;
	}

	public IEventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	public void setEventDispatcher(IEventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getIsDependent() {
		return isDependent;
	}

	public void setIsDependent(Integer isDependent) {
		this.isDependent = isDependent;
	} 
	public Long getRealLotteryId() {
		return realLotteryId;
	}

	public void setRealLotteryId(Long realLotteryId) {
		this.realLotteryId = realLotteryId;
	}

	protected List<GameControlEvent> getGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId){
		return gameControlEventService.getAllUnExcuteGameControlEvents(lotteryId);
	}
	private void ffclog(Long lotteryId,String msg){
		if(lotteryId==99111L ||lotteryId==99114L ||lotteryId==99106L){
			log.info(lotteryId+" "+msg);
		}
	}
	private void debuglog(Long lotteryId,Long reallotteryId,String msg){
		if(lotteryId==10000L && reallotteryId==99106L){
			log.info(lotteryId+" "+msg);
		}
	}
	/**
	 * @Title: execute
	 * @Description: 主控任务采用多彩种并发，单彩种单线程执行，即同一彩种只能有一个主控任务在执行，采用redis控制是否有彩种任务在执行
	 * @throws Exception
	 */
	public void execute() throws Exception {
		log.debug("begin GameControlEventTask----:");
		ffclog(lotteryId, "execute jlffc.");
		//debuglog(lotteryId,realLotteryId, "execute jlffc.");
		// step1:获取所有待执行的主控任务 startissuecode由小到大的顺序
		try { 
			List<GameControlEvent> gameControlEvents=getGameControlEvents(lotteryId,isDependent,realLotteryId);
			ffclog(lotteryId, "execute jlffc.size="+gameControlEvents.size());
			//debuglog(lotteryId,realLotteryId, "execute jlffc.size="+gameControlEvents.size());
			/*//应亨总要求后台恢复自动功能，自动一次--Gary--再次去掉
			if(gameControlEvents == null){
				gameControlEvents = new ArrayList<GameControlEvent>();
			}			
			try {
				gameControlEvents.addAll(gameControlEventService.getReDoGameControlEvents(lotteryId));	
			} catch (Exception e) {
				log.error("get redo event fail" , e);
			}	*/				
			
			// 遍历gameControlEvents
			if(gameControlEvents!=null && gameControlEvents.size()>0){
				for (GameControlEvent gc : gameControlEvents) {
					ffclog(lotteryId, "execute jlffc.size="+gameControlEvents.size()+" type:"+gc.getEnentType());
				//	debuglog(lotteryId,realLotteryId, "execute jlffc.size="+gameControlEvents.size()+" type:"+gc.getEnentType());
					execute(gc);
				}
			}

			log.debug("---end GameControlEventTask----");
		} catch (Exception e) {
			log.error("---task execut error----",e);
			throw e; 
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void execute(GameControlEvent gc){
		log.debug("执行主控任务:"+this.getLotteryId());
		gc.setExecTime(DateUtils.currentDate());
		saveGameIssueRedoLog(gc);
		try {
			eventDispatcher.dispatchEvent(gc);
			gc.setStatus(2L);
			gc.setUpdateTime(DateUtils.currentDate());
			gameControlEventService.save(gc);
			//gameControlEventService.updateTaskStatusDone(gc.getId());// 任务执行成功
			updateGameWarnIssueHandled(gc);

			log.debug("执行主控任务结束");

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("处理事件失败 id="+gc.getId(), e);
			}
			gc.setMessage(e.getMessage());
			gameControlEventService.updateTaskStatusFail(gc);// 任务执行失败
			updateGameWarnIssueExcep(gc);
		}
	}

	protected void updateGameWarnIssueExcep(GameControlEvent gc) {
		if (gc.getWarnIssueId() != null) {// 更新风险奖期表相应记录为处理完成
			try {
				modifyWarnIssueInfo(gc, 3L);
			} catch (Exception e1) {
				log.error("保存异常奖期时失败 id="+gc.getId());
			}
		}
	}

	protected void updateGameWarnIssueHandled(GameControlEvent gc)
			throws Exception {
		if (gc.getWarnIssueId() != null) {// 更新风险奖期表相应记录为处理完成
			modifyWarnIssueInfo(gc, 2L);
		}
	}

	protected void saveGameIssueRedoLog(GameControlEvent gc) {
		try {
			if(gc.getRedoNumber()!=null &&  gc.getRedoNumber()>0){
				
				GameWarnIssueLog gameWarnIssueLog = new GameWarnIssueLog();
				gameWarnIssueLog.setCreateTime(new Date());	
				gameWarnIssueLog.setEvent("10_"+gc.getEnentType());
				gameWarnIssueLog.setLotteryid(gc.getLotteryid());
				gameWarnIssueLog.setIssueCode(gc.getStartIssueCode());				
				GameIssueEntity issue =  gameIssueService.queryGameIssueByLotteryIdAndIssueCode(gc.getLotteryid(), gc.getStartIssueCode());
				gameWarnIssueLog.setWebIssueCode(issue.getWebIssueCode());
				gameWarnIssueLog.setDisposeInfo("重做原因: "+gc.getMessage());
				gameWarnIssueLog.setDisposeMemo(GameWarnEvent.getMessageByCode(gameWarnIssueLog.getEvent()));
				gameWarnIssueLog.setDisposeUser("系统");
				gameWarnIssuelogService.saveGameWarnIssueLog(gameWarnIssueLog);
				
			}
		}catch (Exception e){
			log.error("添加重做记录失败 id="+gc.getId(),e);
		}
	}
	
	
	/**
	 * @Title: modifyWarnIssueInfo
	 * @Description: 修改风险奖期信息状态为已处理，添加处理日志
	 * @param gc
	 * @param status
	 * @throws Exception 
	 */
	private void modifyWarnIssueInfo(GameControlEvent gc, Long status) throws Exception {
		String message = "";
		String message1 = "";
		if(gc.getEnentType() == 4L){
			message = GameWarnEvent.MANUAL_CANCEL_PACKAGE.getCode();
			message1 = "撤销本期方案操作";
		}else if(gc.getEnentType() == 5L){
			message = GameWarnEvent.MANUAL_CANCEL_PLAN.getCode();
			message1 = "撤销预约追号操作";
		}else if(gc.getEnentType() == 6L){
			message = GameWarnEvent.MANUAL_R_AWARD.getCode();
			message1 = "重新开奖操作";
		}else{
			return ;
		}
		
//		else if(gc.getEnentType() == 9L){
//			message = GameWarnEvent.MANUAL_CONTINUE.getCode();
//			message1 = "执行继续开奖操作";
//		}
		
		if(gc.getWarnIssueId() == null){
			return ;
		}
		
		if(gc.getWarnIssueId() > 0 ){
			GameWarnIssue gameWarnIssue = gameWarnIssueService.getGameWarnIssueById(gc.getWarnIssueId());			
			if(gameWarnIssue.getStatusRout() != null){
				gameWarnIssue.setStatusRout(gameWarnIssue.getStatusRout() +","+ message);
			}
			gameWarnIssue.setUpdateTime(new Date());
			gameWarnIssue.setStatus(2L);
			gameWarnIssueService.updateGameWarnIssue(gameWarnIssue);
						
		}
		// 添加风险奖期处理日志为成功失败
		GameWarnIssueLog gameWarnIssueLog = new GameWarnIssueLog();
		gameWarnIssueLog.setCreateTime(new Date());	
		gameWarnIssueLog.setEvent(message);
		gameWarnIssueLog.setLotteryid(gc.getLotteryid());
		gameWarnIssueLog.setIssueCode(gc.getStartIssueCode());
		
		GameIssueEntity issue =  gameIssueService.queryGameIssueByLotteryIdAndIssueCode(gc.getLotteryid(), gc.getStartIssueCode());
		gameWarnIssueLog.setWebIssueCode(issue.getWebIssueCode());
		gameWarnIssueLog.setDisposeInfo(gc.getMessage());
		gameWarnIssuelogService.saveGameWarnIssueLog(gameWarnIssueLog);
		
	}

}
