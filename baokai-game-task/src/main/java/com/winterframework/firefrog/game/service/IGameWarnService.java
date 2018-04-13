package com.winterframework.firefrog.game.service;

import java.util.Set;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;

public interface IGameWarnService {

	/** 
	* @Title: doBusiness 
	* @Description: 风控审核 验奖过程  调用派奖、返点派奖
	* @param order
	* @return
	*/
	void doBusiness(GameOrder order,boolean isRedraw) throws GameRiskException;
	void doBusiness(GameContext ctx,GameOrder order,boolean isRedraw) throws GameRiskException;
	/** 
	* @Title: countAndClear 
	* @Description: 统计并清空缓存
	* @param lotteryId
	* @param issueCode
	* @param userIds
	*/
	void countAndClear(Long lotteryId, Long issueCode,Set<Long> userIds);
	/** 
	* @Title: queryGameRiskConfig 
	* @Description: 获取审核配置信息
	* @return
	*/
	GameRiskConfig queryGameRiskConfig(Long lotteryId);
	
	/** 
	* @Title: cancelWarnOrder 
	* @Description: 撤销风险订单
	* @param orderId
	*/
	void cancelWarnOrder(Long orderId);
	
	/** 
	* @Title: clearWarn 
	* @Description: 清空一期所有审核数据，包含缓存
	* @param lotteryId
	* @param issueCode
	*/
	void clearWarn(Long lotteryId, Long issueCode);
	
	/** 
	* @Title: clearWarnCache 
	* @Description: 清空一期审核缓存
	* @param lotteryId
	* @param issueCode
	*/
	void clearCacheWarnServiceBeansByLotteryAndIssue(Long lotteryId, Long issueCode);
	
	void updateIssuseRedisCache(GameOrder order);
}
