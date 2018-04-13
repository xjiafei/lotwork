package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameControlEventDao 
* @Description: 游戏控制事件dao
* @author Alan
* @date 2013-11-18 下午2:22:28 
*  
*/
public interface IGameControlEventDao extends BaseDao<GameControlEvent> {

	/**
	* @Title: addControlEvent 
	* @Description: 添加控制事件
	 */
	public void addControlEvent(GameControlEvent event) throws Exception;

	/** 
	* @Title: getAllUnExcuteGameControlEvents 
	* @Description: 获取所有待执行的control任务
	* @return
	*/
	public List<GameControlEvent> getAllUnExcuteGameControlEvents(Long lotteryId);
	public List<GameControlEvent> getTrendUnExcuteGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId);
	public List<GameControlEvent> getIssueEndUnExcuteGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId);
	/** 
	* @Title: getReDoGameControlEvents 
	* @Description: 获取重做任务
	* @param lotteryId
	* @param nextDoTime
	* @return
	*/
	public List<GameControlEvent> getReDoGameControlEvents(Long lotteryId ,Date nextDoTime);
	List<GameControlEvent> getReDoGameControlEvents(Long lotteryId,Integer isDependent,Long realLotteryId,Date nextDoTime);
	/**
	 * 获取相同的任务（根据彩种和奖期判断）
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 */
	GameControlEvent getSameEventUnexecByLotteryIdAndIssueCode(Long lotteryId,Long issueCode);
	GameControlEvent getSameEventUnexecByLotteryIdAndIssueCodeTrend(Long lotteryId,Long issueCode,Long realLotteryId);
	GameControlEvent getUnexecByLotteryIdAndIssueCodeAndEventType(Long lotteryId,Long issueCode,Long realLotteryId,Integer eventType);
	
	public void updateTaskStatusDone(Long id);

	public void updateTaskStatusDoing(Long id);

	public void updateTaskStatusFail(Long id);

	public void updateTaskStatusUndo(Long id);
	/** 
	* @Title: updateTrendTaskStatusNotCare 
	* @Description: 重开或补开任务执行时，忽略掉该彩种后续奖期的走势图任务
	* @param gc
	*/
	void updateTrendTaskStatusNotCare(GameControlEvent gc);
	
	/**
	 * 判断是否存在走势图事件
	 * @param lotteryId
	 * @return 存在？1：0
	 * @throws Exception
	 */
	public Integer checkTreadTaskExisted(Long lotteryId,Long issueCode) throws Exception;
	
	/**
	 * 取得未執行event by lotteryId And eventType
	 * @param lotteryId
	 * @param eventType
	 * @return
	 */
	public List<GameControlEvent> getUnExcuteControlEvents(Long lotteryId,EventType eventType);
	
	/**
	 * 取得尚未初始event by lotteryId And eventType
	 * @param lotteryId
	 * @param eventType
	 * @return
	 */
	public List<GameControlEvent> getInitExcuteControlEvents(Long lotteryId,EventType eventType);


}
