package com.winterframework.firefrog.schedule.gameIssue;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.service.IGameIssueFacadeService;

/**
 * 监控奖期销售时间截止发送邮件任务
 * @author 
 * @date 2013-11-18 下午5:15:32 
 */
public class SalesTimeEndEmailSendTask implements Serializable {

	private static final long serialVersionUID = 6272140419766924725L;

	private static final Logger logger = LoggerFactory.getLogger(SalesTimeEndEmailSendTask.class);

	@Resource(name = "gameIssueFacadeServiceImpl")
	private IGameIssueFacadeService gameIssueFacadeService;

	public void execute() throws Exception {
		logger.debug("Entered Method :: SalesTimeEndEmailSendTask.execute");
		logger.debug("发送邮件任务调度开始 ");
		try{
			gameIssueFacadeService.sendSsqSaleEndEmail();
		}catch(Exception e){
			logger.error("发送邮件任务调度失败 ", e);
		}
		logger.debug("Exited Method ::  SalesTimeEndEmailSendTask.execute");
	}
}
