package com.winterframework.firefrog.game.dao.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;

/** 
* @ClassName: GameMoneyAndMultpileUtils 
* @Description: 处理圆角模式以及倍数的工具类
* @author 你的名字 
* @date 2014-4-7 上午11:46:09 
*  
*/
@Repository("gameAssistProcess")
public class GameAssistProcess {
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** 
	* @Title: getBonus 
	* @Description: 获得预期奖金
	* @param detail
	* @param userId
	* @return
	*/
	public Long getBonus(Long lotteryId, String betTypeCode, Long userId, Long moneyMode, Long multiple) {
		//奖金 = 实际奖金* 倍数 * 注单数  
		Long actualBonus = gameAwardDao.getActualBonus(lotteryId, betTypeCode, userId);
		return getBonus(actualBonus, moneyMode, multiple);
	}
	/**
	 * @param lotteryId
	 * @param betTypeCode
	 * @param userId
	 * @param moneyMode	奖金模式=原始奖金+返点奖金
	 * @param multiple
	 * @param retAward
	 * @return
	 */
	public Long getBonus(Long lotteryId, String betTypeCode, Long userId, Long moneyMode, Long multiple,Long retAward) {
		//奖金 = 实际奖金* 倍数 * 注单数  
		Long actualBonus = gameAwardDao.getActualBonus(lotteryId, betTypeCode, userId)+retAward;
		return getBonus(actualBonus, moneyMode, multiple);
	}
	public Long getBonusDown(Long lotteryId, String betTypeCode, Long userId, Long moneyMode, Long multiple,Long retAward) {
		//奖金 = 实际奖金* 倍数 * 注单数  
		Long actualBonus = gameAwardDao.getActualBonusDown(lotteryId, betTypeCode, userId)+retAward;
		return getBonus(actualBonus, moneyMode, multiple);
	}
	/**
	 * @param lotteryId
	 * @param betTypeCode
	 * @param userId
	 * @param moneyMode	奖金模式=原始奖金+返点奖金
	 * @param multiple
	 * @param retAward
	 * @return
	 */
	public Long getBonus(Long actualBonus, Long moneyMode, Long multiple) {
		if (actualBonus == null) {
			actualBonus = 0L;
		}
		Long bonues = 0L;
		if (1L == moneyMode) {
			bonues = actualBonus * multiple;
		} else if (2L == moneyMode) {
			bonues = actualBonus * multiple / 10;
		} else if (3L == moneyMode) {
			bonues = actualBonus * multiple / 100;
		} else {
			log.error("不支持此货币模式,元角模式为" + moneyMode);
		}
		return bonues;
	}
}
