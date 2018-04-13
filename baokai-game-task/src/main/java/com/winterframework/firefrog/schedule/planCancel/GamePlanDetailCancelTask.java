package com.winterframework.firefrog.schedule.planCancel;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;

/** 
* @ClassName: GameIssuesGenerateSchedule 
* @Description:奖期自动生成计划任务表 
* @author david 
* @date 2013-10-23 上午10:31:32 
*  
*/
@Service("gamePlanDetailCancelSchedule")
public class GamePlanDetailCancelTask {

	private Logger log = LoggerFactory.getLogger(GamePlanDetailCancelTask.class);

	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;

	public void execute() throws Exception {
		log.debug("---begin GamePlanDetailCancelTask----");
		gamePlanService.reservationCalled();;
		log.debug("---end GamePlanDetailCancelTask----");
	}

}
