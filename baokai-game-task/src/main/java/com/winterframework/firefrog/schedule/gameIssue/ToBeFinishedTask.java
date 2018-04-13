package com.winterframework.firefrog.schedule.gameIssue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.service.IGameIssueFacadeService;

/** 
 * 待结束奖期任务 
 * @author Denny 
 * @date 2013-11-19 下午4:06:29 
 */
public class ToBeFinishedTask {

	private static final Logger logger = LoggerFactory.getLogger(ToBeFinishedTask.class);

	@Resource(name = "gameIssueFacadeServiceImpl")
	private IGameIssueFacadeService gameIssueFacadeService;

	public void execute() throws Exception {
		logger.info("ToBeFinishedTask execute start... ");

		gameIssueFacadeService.toBeFinished();

		logger.info("ToBeFinishedTask execute end... ");
	}
}
