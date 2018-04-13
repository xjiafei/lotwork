package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
/**
 * 
* @ClassName: IGameDrawResultProcess 
* @Description: 游戏开奖流程入口处理接口
* @author Richard
* @date 2013-11-4 上午11:42:50 
*
 */
public interface IGameDrawResultService {
	
	/**
	 * 
	* @Title: gameDrawResult 
	* @Description: 游戏开奖主流程处理入口
	* @param result
	* @throws Exception
	 */
	public void gameDrawResult(GameDrawResult result) throws Exception;
	 
	/**
	 * 获取最近几期的开奖结果（按彩种）
	 * @param lotteryId
	 * @param top 最近几期（按倒序）
	 * @return
	 * @throws Exception
	 */
	List<GameDrawResult> getGameDrawResultTopByLotteryId(Long lotteryId,Integer top) throws Exception;
	
	
	/**
	 * 
	 * @Title: getnumberRecordByLotteryIdAndIssueCode
	 * @Description:获取开奖号码
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception;
}
