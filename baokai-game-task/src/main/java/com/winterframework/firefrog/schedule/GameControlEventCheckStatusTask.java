package com.winterframework.firefrog.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueService;

/**
 * @ClassName: GameControlEventCheckStatusTask
 * @Description: 任務狀態檢核
 * @author hugh
 * @date 2013-11-21 下午8:07:29
 * 
 */
public class GameControlEventCheckStatusTask {

	private Logger log = LoggerFactory
			.getLogger(GameControlEventCheckStatusTask.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService;

	/**
	 * @Title: execute
	 * @Description: 任務狀態檢核
	 * @throws Exception
	 */
	public void execute() throws Exception {
		log.info("----begin GameControlEventCheckStatusTask----:");
		checkIssueAndUpdateEventStatus(null, new CheckECDrawStatusRule());
		log.info("----end GameControlEventCheckStatusTask----");
	}

	private void checkIssueAndUpdateEventStatus(Long lotteryId, CheckRule rule) {
		try {
			log.info("----begin checkIssueAndUpdateEventStatus----:"
					+ lotteryId + "," + rule.getEventType().name());
			List<GameControlEvent> events = gameControlEventService
					.getInitExcuteControlEvents(lotteryId, rule.getEventType());
			log.info("----events----:" + events.size());
			for (GameControlEvent event : events) {
				GameIssueEntity issue = gameIssueService
						.queryGameIssueByLotteryIdAndIssueCode(
								event.getLotteryid(), event.getStartIssueCode());
				if (rule.checkStatus(issue.getStatus())) {
					rule.update(event);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class CheckECDrawStatusRule implements CheckRule {

		@Override
		public EventType getEventType() {
			return EventType.EC_DRAW;
		}

		@Override
		public boolean checkStatus(GameIssueStatus status) {
			return GameIssueStatus.SALE_END.equals(status);
		}

		@Override
		public void update(GameControlEvent event) {
			log.info("update:" + event.getId() + " status from"
					+ event.getStatus() + " to" + 0);
			gameControlEventService.updateTaskStatusUndo(event.getId());
		}

	}

	private interface CheckRule {
		EventType getEventType();

		boolean checkStatus(GameIssueStatus status);

		void update(GameControlEvent event);
	}
}
