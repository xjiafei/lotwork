package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAward;

 
/**
 * 奖金服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年9月28日
 */
public interface IGameAwardService {

	
	/**
	 * @param lotteryId
	 * @param awardGroupId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	GameAward getValidByLotteryIdAndGroupIdAndBetTypeCode(Long lotteryId,Long awardGroupId,String betTypeCode) throws Exception; 
	List<GameAward> getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(Long lotteryId,Long awardGroupId,String betTypeCode) throws Exception;
	
	/**
	 * 获取多奖金的最大奖金记录
	 * @param lotteryId
	 * @param awardGroupId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	GameAward getMaxBonusAwardByLotteryIdAndGroupIdAndBetTypeCodeParent(Long lotteryId,Long awardGroupId,String betTypeCode) throws Exception; 
}
