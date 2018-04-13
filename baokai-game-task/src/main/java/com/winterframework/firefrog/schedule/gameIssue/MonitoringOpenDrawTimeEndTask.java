package com.winterframework.firefrog.schedule.gameIssue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.IGameIssueFacadeService;
import com.winterframework.firefrog.game.service.IGameSimulateService;
import com.winterframework.firefrog.game.service.impl.GameIssueFacadeServiceImpl;

/** 
 * 监控奖期理论开奖时间截止任务 
 * @author Denny 
 * @date 2013-11-18 下午5:15:32 
 */
public class MonitoringOpenDrawTimeEndTask implements Serializable {

	private static final long serialVersionUID = 6272140419766924725L;

	private static final Logger logger = LoggerFactory.getLogger(MonitoringOpenDrawTimeEndTask.class);

	@Resource(name = "gameIssueFacadeServiceImpl")
	private IGameIssueFacadeService gameIssueFacadeService;

	@Resource(name = "gameSimulateServiceImpl")
	private IGameSimulateService gameSimulateServiceImpl;
	private Long lotteryId;

	public void execute() throws Exception {
		logger.info("MonitoringOpenDrawTimeEndTask start lotteryid="+lotteryId);
		logger.debug("Entered Method :: MonitoringOpenDrawTimeEndTask.execute");
		logger.debug("理论开奖调度开始 ");
		List<GameIssueEntity> _openDrawIssues = new ArrayList<GameIssueEntity>();
		try{
			Map<String, List<GameIssueEntity>> result = gameIssueFacadeService.monitoringOpenDrawTimeEnd(lotteryId);
			_openDrawIssues = result.get(GameIssueFacadeServiceImpl.RESULT_OPEN_DRAW_ISSUES);
		}catch(Exception e){
			logger.error("理论开奖调度失败 ", e);
		}
		String isSimulateDraw=System.getProperty("simulateDraw");  
		if("true".equals(isSimulateDraw)){
			logger.debug("模拟开奖  开始 ");
			//模拟开奖  本地运行测试可放开注释
			try{
				gameSimulateServiceImpl.simulaterOpenArward(_openDrawIssues);
			}catch(Exception e){
				logger.error("模拟开奖失败 ", e);
			}
		}
		logger.debug("Exited Method ::  MonitoringOpenDrawTimeEndTask.execute");
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
