package com.winterframework.firefrog.game.service;

import java.util.Date;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;

/**
 *IGameControlEventService
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月21日
 */
public interface IGameControlEventService {
	int save(GameControlEvent event) throws Exception;
	/**
	 * 更新状态
	 * @param lotteryid
	 * @param startIssueCode
	 * @param eventType
	 * @param realLotteryId
	 * @param fromStatus
	 * @param toStatus 
	 * @return
	 */
	int changeStatus(Long lotteryId, Long startIssueCode,Long eventType,Long realLotteryId,Integer fromStatus,Integer toStatus) throws Exception;
	/**
	 * 重做开奖任务
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	void retryDraw(Long lotteryId,Long issueCode) throws Exception;
	/**
	 * 追号事件
	 * @param lotteryId
	 * @param issueCode
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	boolean addPlanEvent(Long lotteryId,Long issueCode,Long planId) throws Exception;
	/**
	 * 奖期盈亏表事件
	 * @param issue
	 * @return
	 */
	boolean addCreateWinReportEvent(GameIssue issue);
}
