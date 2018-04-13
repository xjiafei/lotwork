package com.winterframework.firefrog.schedule.gameIssue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.service.IGameIssueService;

/** 
* @ClassName: GameIssuesGenerateSchedule 
* @Description:奖期自动生成计划任务表 
* @author david 
* @date 2013-10-23 上午10:31:32 
*  
*/
@Service("gameIssuesGenerateSchedule")
public class GameIssuesGenerateTask {

	private Logger log = LoggerFactory.getLogger(GameIssuesGenerateTask.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	@Resource(name = "gameIssueRuleDaoImpl")
	private IGameIssueRuleDao gameIssueRuleDaoImpl;
	
	
	public void execute() throws Exception {
		log.debug("---begin GameIssuesGenerateSchedule----");
		gameIssueService.generateGameIssue();
		log.debug("---end GameIssuesGenerateSchedule----");
		gameIssueRuleDaoImpl.checkUpdateCommen();
	}

}
