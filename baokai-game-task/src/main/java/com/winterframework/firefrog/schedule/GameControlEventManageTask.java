package com.winterframework.firefrog.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.event.service.IEventDispatcher;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;
import com.winterframework.firefrog.game.service.IGameWarnIssueService;

/**
 * @ClassName: GameControlEventTask
 * @Description: 任务调度生成器
 * @author hugh
 * @date 2013-11-21 下午8:07:29
 * 
 */
public class GameControlEventManageTask {

	private Logger log = LoggerFactory.getLogger(GameControlEventManageTask.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;


	/**
	 * @Title: execute
	 * @Description: 任务调度生成器
	 * @throws Exception
	 */
	public void execute() throws Exception {
		log.debug("----begin GameControlEventManageTask----:");
		gameIssueService.dealNoAwardNumberSaleEndIssue();
		log.debug("----end GameControlEventManageTask----");
	}


}
