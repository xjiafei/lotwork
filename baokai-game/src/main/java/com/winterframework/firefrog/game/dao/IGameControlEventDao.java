package com.winterframework.firefrog.game.dao;

import java.util.Date;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
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
	void retryByLotteryIssueCode(Long lotteryid, Long issueCode)
			throws Exception;
	/**
	 * 
	* @Title: queryEventByCondition 
	* @Description: 根据条件查询对应的事件
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @return GameControlEvent
	* @throws Exception
	 */
	public GameControlEvent queryEventByCondition(Long lotteryid, Long startIssueCode, Long endIssueCode) throws Exception;
	
	/**
	 * 更新状态
	 * @param lotteryid
	 * @param startIssueCode
	 * @param eventType
	 * @param realLotteryId
	 * @param fromStatus
	 * @param toStatus
	 * @param curDate 当前时间
	 * @return
	 */
	int changeStatus(Long lotteryId, Long startIssueCode,Long eventType,Long realLotteryId,Integer fromStatus,Integer toStatus,Date curDate);
	GameControlEvent getUnexecByLotteryIdAndIssueCodeAndEventType(Long lotteryId,Long issueCode,Long realLotteryId,Integer eventType);

}
