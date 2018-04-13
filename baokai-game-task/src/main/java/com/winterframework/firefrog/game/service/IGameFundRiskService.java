package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public interface IGameFundRiskService {
	public void fundRequest(List<TORiskDTO> toRiskDTOList) throws Exception;
	public boolean fundRequest(GameContext ctx,Map<String,List<TORiskDTO>> toRiskDTOMap) throws Exception;
	public boolean fundRequestAward(GameContext ctx,Map<String,List<TORiskDTO>> toRiskDTOMap) throws Exception;
	public void fundRequestBatches(GameContext ctx,List<TORiskDTO> toRiskDTOList,int requestType) throws Exception;
	public void fundRequestBatches2(GameContext ctx,List<TORiskDTO> toRiskDTOList,int requestType) throws Exception;
	
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
	public void addCoin(Long amount, Long lotteryId, String orderCode, Integer type, Long userId,Long issueCode) throws Exception;
	
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
	* @Title: distributeAward 
	* @Description: 派奖
	* @param toRiskDTOList
	* @throws Exception
	 */
	public void distributeAward(List<TORiskDTO> toRiskDTOList) throws Exception;
	
	/** 
	* @Title: betAmountFreezer 
	* @Description:投注冻结
	* @param toRiskRequest
	* @throws Exception
	*/
	public void betAmountFreezer(List<TORiskDTO> toRiskDTOList) throws Exception;
	
	/** 
	* @Title: cancelFee 
	* @Description:撤销扣款
	* @param toRiskRequest
	* @throws Exception
	*/
	 public void cancelFee(List<TORiskDTO> toRiskDTOList) throws Exception;
	 boolean fundRequestSubmap(GameContext ctx,Map<String, List<TORiskDTO>> subDtoMap,boolean isAward) throws Exception;
	 void doFundFailed(GameContext ctx,Map<String, List<TORiskDTO>> subDtoMap,List<String> orderCodeList,String errMsg);	 
}
