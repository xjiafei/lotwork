package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;

/** 
* @ClassName: IBetLimitService 
* @Description: 投注限制Service接口 
* @author Denny 
* @date 2013-8-20 下午2:11:03 
*  
*/
public interface IBetLimitService {

	public List<BetLimit> queryBetLimit(long lotteryid) throws Exception;

    /**
     * 修改投注限制
     * @param betLimitList 投注限制列表
     * @param lotteryId 彩种ID
     * @throws Exception
     */
	public void modifyBetLimit(List<BetLimit> betLimitList, Long lotteryId) throws Exception;
	
	public void checkBetLimit(Long lotteryid, Long auditType) throws Exception;
	
	public void publishBetLimit(Long lotteryid, Long publishType) throws Exception;
	
	public List<BetLimit> queryBetLimitByBet(long lotteryid) throws Exception;
	
	/**
	 * 
	* @Title: getMaxGameIssue
	* @Description: 获取最大奖期值
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public Long getMaxGameIssue(Long  lotteryId) throws Exception;

    /**
     * 投注限制状态
     * @param lotteryId 彩种ID
     * @return
     * @throws Exception
     */
	public Integer betLimitStatus(Long lotteryId) throws Exception;
	
	public void modifyBetLimitAssist(List<BetLimitAssist> assists) throws Exception;
}
