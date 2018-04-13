package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;

public interface IGameFundRiskService {
	
	/**
	 * 
	* @Title: addCoin 扣减游戏币 
	* @throws Exception
	 */
	public void reduceCoin(Long amount, Long lotteryId, Long issueCode, String orderCode, Integer type, Long userId) throws Exception;

	/**
	 * 
	* @Title: addCoin 增加游戏币 
	* @throws Exception
	 */
	public void addCoin(Long amount, Long lotteryId, String orderCode, Integer type, Long userId) throws Exception;
	
	/**
	 * 
	* @Title: unFreezeFundGamePlan 
	* @Description: 解冻游戏追号。
	* @param amount
	* @param issueCode
	* @param lotteryId
	* @param planCode
	* @param type
	* @param userId
	* @throws Exception
	 */
	public void unFreezeFundGamePlan(Long amount, Long issueCode, Long lotteryId, String planCode, Integer type, Long userId) throws Exception;
	
	/**
	 * 
	* @Title: freezeFundGameOrder 
	* @Description: 冻结游戏订单金额
	* @param amount
	* @param issueCode
	* @param lotteryId
	* @param orderCode
	* @param type
	* @param userId
	* @throws Exception
	 */
	public void freezeFundGameOrder(Long amount, Long issueCode, Long lotteryId,String orderCode, Integer type, Long userId) throws Exception;
	
	
	/**
	 * 
	* @Title: unFreezeFundGamePlan 
	* @Description: 解冻游戏追号。
	* @param amount
	* @param issueCode
	* @param lotteryId
	* @param planCode
	* @param type
	* @param userId
	* @throws Exception
	 */
	public void unFreezeFundGameOrder(Long amount, Long issueCode, Long lotteryId, String orderCode, Integer type, Long userId) throws Exception;

	/** 
	* @Title: freezeFundGameplan 
	* @Description:冻结追号资金 
	* @param amount
	* @param issueCode
	* @param lotteryId
	* @param orderCode
	* @param type
	* @param userId
	* @throws Exception
	*/
	void freezeFundGameplan(Long amount, Long issueCode, Long lotteryId, String orderCode, Integer type, Long userId)
			throws Exception;
	
	
	/** 
	* @Title: betAmountReduce 
	* @Description:投注扣款
	* @param toRiskRequest
	* @throws Exception
	*/
	void betAmountReduce(List<TORiskDTO> toRiskDTOList)
			throws Exception;
	
	/** 
	* @Title: distributeAward 
	* @Description:派发奖金
	* @param toRiskRequest
	* @throws Exception
	*/
	void distributeAward(List<TORiskDTO> toRiskDTOList)
			throws Exception;
	
	/** 
	* @Title: distributeAward 
	* @Description:秒秒彩派发奖金
	* @param toRiskRequest
	* @throws Exception
	*/
	void distributeAwardMMC(List<TORiskDTO> toRiskDTOList)
			throws Exception;
	
	/** 
	* @Title: betAmountFreezer 
	* @Description:投注冻结
	* @param toRiskRequest
	* @throws Exception
	*/
	void betAmountFreezer(List<TORiskDTO> toRiskDTOList)
			throws Exception;
	
	/** 
	* @Title: cancelFee 
	* @Description:撤销扣款
	* @param toRiskRequest
	* @throws Exception
	*/
	void cancelFee(List<TORiskDTO> toRiskDTOList)
			throws Exception;
	
	/** 
	* @Title: activityFund 
	* @Description: 活动派奖
	* @param toRiskDTOList
	* @throws Exception
	*/
	void activityFund(List<TORiskDTO> toRiskDTOList) throws Exception;
}
