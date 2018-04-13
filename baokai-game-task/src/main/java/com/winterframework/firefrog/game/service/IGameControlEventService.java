package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;

/** 
* @ClassName: IGameControlEventService 
* @Description: 主控任务service 
* @author david 
* @date 2013-11-21 下午8:09:43 
*  
*/
public interface IGameControlEventService {
	int save(GameControlEvent event) throws Exception;
	/** 
	* @Title: getAllUnExcuteGameControlEvents 
	* @Description: 获取所有待执行的任务列表 
	* @return
	*/
	public List<GameControlEvent> getAllUnExcuteGameControlEvents(Long lotteryId); 
	List<GameControlEvent> getAllUnExcuteGameControlEventsTrend(Long lotteryId,Integer isDependent,Long realLotteryId);
	List<GameControlEvent> getAllUnExcuteGameControlEventsIssueEnd(Long lotteryId,Integer isDependent,Long realLotteryId);
	
	List<GameControlEvent> getReDoGameControlEvents(Long lotteryId );
	List<GameControlEvent> getReDoGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId);
	List<GameControlEvent> getToExcuteControlEvents(Long lotteryId );
	List<GameControlEvent> getToExcuteControlEvents(Long lotteryId,EventType eventType);
	List<GameControlEvent> getInitExcuteControlEvents(Long lotteryId,EventType eventType);
	/**
	 * 获取相同的任务（根据彩种和奖期判断）
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	GameControlEvent getSameEventUnexecByLotteryIdAndIssueCode(Long lotteryId,Long issueCode);
	GameControlEvent getSameEventUnexecByLotteryIdAndIssueCodeTrend(Long lotteryId,Long issueCode,Long realLotteryId);
	GameControlEvent getUnexecByLotteryIdAndIssueCodeAndTrendType(Long lotteryId,Long issueCode,Long realLotteryId,Integer eventType);
	
	/** 
	* @Title: updateTaskStatusDone 
	* @Description:更新任务状态为已完成
	* @param id
	*/
	public void updateTaskStatusDone(Long id);

	/** 
	* @Title: updateTaskStatusDoing 
	* @Description:更新任务状态为进行中
	* @param id
	*/
	public void updateTaskStatusDoing(Long id);

	/** 
	* @Title: updateTaskStatusFail 
	* @Description: 更新任务状态为失败
	* @param id
	*/
	public void updateTaskStatusFail(Long id);
	void updateTaskStatusFail(GameControlEvent gc) ;
	/** 
	* @Title: updateTrendTaskStatusNotCare 
	* @Description: 重开或补开任务执行时，忽略掉该彩种后续奖期的走势图任务
	* @param gc
	*/
	void updateTrendTaskStatusNotCare(GameControlEvent gc);
	boolean addCreateWinReportEvent(GameIssue issue);
	boolean addGameTrendChartRegenerateEvent(GameIssue issue);

	/** 
	* @Title: updateTaskStatusDoing 
	* @Description:更新任务状态为待進行
	* @param id
	*/
	public void updateTaskStatusUndo(Long id);
}
