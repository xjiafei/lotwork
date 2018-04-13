package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;

/** 
* @ClassName: IGameDrawService 
* @Description: 历史开奖号码相关统计Service接口 
* @author Denny 
* @date 2013-7-22 下午3:52:14 
* 
*/
public interface IGameDrawService {

	/**
	 * 
	* @Title: getAllByLotteryIdAndCountIssue 
	* @Description: 投注方式查询
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameDrawResult> getAllByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception;

	public List<GameDrawResult> getDrawResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;

	/**
	 * 
	* @Title: getAllByLotteryIdAndCountIssue 
	* @Description: 投注方式查询
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameDrawResult> getTrendResultByLotteryIdAndCountIssue(Long lotteryId, Integer count) throws Exception;

	public List<GameDrawResult> getTrendResultByDate(Long lotteryId, Date startDate, Date endDate) throws Exception;
}
