package com.winterframework.firefrog.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.service.IGameIssueService;

/**
 * 吉利骰寶主動跟RNG要開獎號碼
 * @author Ami.Tsai
 *
 */
public class JlsbGrabDrawNumberTask extends AbstractGrabDrawNumberTask{ 
		@SuppressWarnings("unused")
		private Logger log = LoggerFactory.getLogger(JlsbGrabDrawNumberTask.class);
		
		@Resource(name="gameIssueServiceImpl")
		private IGameIssueService gameIssueService;
		
		@Override
		protected List<GameIssue> getGameIssue() throws Exception{
			return gameIssueService.getNoNumberLatestByLotteryIdAndTime(lotteryId,DateUtils.currentDate());
		}
}
