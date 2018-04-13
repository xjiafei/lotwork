package com.winterframework.firefrog.schedule.gameSeries;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;

/** 
* @ClassName: GameIssuesGenerateSchedule 
* @Description:奖期自动生成计划任务表 
* @author david 
* @date 2013-10-23 上午10:31:32 
*  
*/
@Service("gameSeriesChangeStatusSchedule")
public class GameSeriesChangeStatusTask {

	private Logger log = LoggerFactory.getLogger(GameSeriesChangeStatusTask.class);

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService gameSeriesConfigService;

	public void execute() throws Exception {
		log.debug("---begin gameSeriesChangeStatusSchedule----");
			gameSeriesConfigService.gameSeriesChangeStatus();
		log.debug("---end gameSeriesChangeStatusSchedule----");
	}

}
