package com.winterframework.firefrog.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.service.IGameIssueService;  

public class JlsbVipGrabDrawNumberTask extends AbstractGrabDrawNumberTask{ 
		private Logger log = LoggerFactory.getLogger(JlsbVipGrabDrawNumberTask.class);

		@Resource(name="gameIssueServiceImpl")
		private IGameIssueService gameIssueService;
		
		@Override
		protected List<GameIssue> getGameIssue() throws Exception {
			return gameIssueService.getNoNumberLatestByLotteryIdAndTime(lotteryId,DateUtils.currentDate());
		}
		@Override
		public String getBriefCode() {
			return "JLSB";
		}
}
