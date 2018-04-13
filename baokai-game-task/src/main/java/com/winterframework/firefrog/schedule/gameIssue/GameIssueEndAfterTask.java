package com.winterframework.firefrog.schedule.gameIssue;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.winterframework.firefrog.game.service.IGameIssueEndAfterService;

/** 
 * @author 
 * @date 2013-11-18 下午5:15:32 
 */
public class GameIssueEndAfterTask implements Serializable { 
	private static final long serialVersionUID = -4453488854021845470L;

	private static final Logger logger = LoggerFactory.getLogger(GameIssueEndAfterTask.class);

	@Resource(name = "gameIssueEndAfterServiceImpl")
	private IGameIssueEndAfterService gameIssueEndAfterService;

	public void execute() throws Exception {
		/*logger.debug("Entered Method :: GameIssueEndAfterTask.execute"); 
		try{
			this.gameIssueEndAfterService.doBusiness();
		}catch(Exception e){
			logger.error("发送邮件任务调度失败 ", e);
		}
		
		log.debug("begin GameControlEventTask----:");
		// step1:获取所有待执行的主控任务 startissuecode由小到大的顺序
		try {
			List<GameControlEvent> gameControlEvents=null;
			if(lotteryId==10000L){
				gameControlEvents = gameControlEventService.getAllUnExcuteGameControlEventsTrend(lotteryId,isDependent,realLotteryId);
			}else{
				gameControlEvents = gameControlEventService.getAllUnExcuteGameControlEvents(lotteryId);	
			}
			if(gameControlEvents == null){
				gameControlEvents = new ArrayList<GameControlEvent>();
			}
			在资金处理未完善前，先去掉重做功能--Gary
			 * try {
				gameControlEvents.addAll(gameControlEventService.getReDoGameControlEvents(lotteryId));	
			} catch (Exception e) {
				log.error("get redo event fail" , e);
			}
						
			
			// 遍历gameControlEvents
			if(gameControlEvents!=null && gameControlEvents.size()>0){
				for (GameControlEvent gc : gameControlEvents) {
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
		try {
			eventDispatcher.dispatchEvent(gc);

			gameControlEventService.updateTaskStatusDone(gc.getId());// 任务执行成功
			if (gc.getWarnIssueId() != null) {// 更新风险奖期表相应记录为处理完成
				modifyWarnIssueInfo(gc, 2L);
			}

			log.debug("执行主控任务结束");

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("处理事件失败 id="+gc.getId(), e);
			}
			gc.setMessage(e.getMessage());
			gameControlEventService.updateTaskStatusFail(gc);// 任务执行失败
			if (gc.getWarnIssueId() != null) {// 更新风险奖期表相应记录为处理完成
				try {
					modifyWarnIssueInfo(gc, 3L);
				} catch (Exception e1) {
					log.error("保存异常奖期时失败 id="+gc.getId());
				}
			}
		}
	}
		logger.debug("Exited Method ::  GameIssueEndAfterTask.execute");*/
	}
}
