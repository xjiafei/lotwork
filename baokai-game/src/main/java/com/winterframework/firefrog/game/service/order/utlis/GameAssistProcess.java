package com.winterframework.firefrog.game.service.order.utlis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.entity.MoneyMode;

/**
 * 处理元角模式以及倍数的工具类
 * @author 你的名字 
 * @date 2014-4-7 上午11:46:09 
 */
@Repository("gameAssistProcess")
public class GameAssistProcess {
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 获得预期奖金
	 * @param lotteryId
	 * @param betTypeCode
	 * @param userId
	 * @param moneyMode
	 * @param multiple
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
	 * 計算 actualBonus * multiple 後元角模式下的數據。
	 * @param actualBonus 實際獎金
	 * @param moneyMode 元角模式；1:元、2:角
	 * @param multiple 倍數
	 * @return
	 */
	public Long getBonus(Long actualBonus, Long moneyMode, Long multiple) {
		Long bonues = 0L;
		if (MoneyMode.YUAN.getValue() == moneyMode.intValue()) {
			bonues = actualBonus * multiple;
		} else if (MoneyMode.JIAO.getValue() == moneyMode.intValue()) {
			bonues = actualBonus * multiple / 10;
		} else if (MoneyMode.FEN.getValue() == moneyMode.intValue()) {
			bonues = actualBonus * multiple / 100;
		} else {
			log.error("不支持此货币模式,元角模式为" + moneyMode);
		}
		return bonues;
	}
	
	/**
	 * 反推出賠率。
	 * @param singleWin 單注獎金(注意是否來源為高獎金模式，若是則該金額要先減掉 retAward[返點獎金])
	 * @param moneyMode 元角模式；1:元、2:角
	 * @param multiple 倍數
	 * @return
	 */
	public Long getOdds(Long singleWin, MoneyMode moneyMode, Long multiple) {
		Long _result = 0L;
		
		if(MoneyMode.YUAN == moneyMode) {
			_result = singleWin / multiple;
		} else if (MoneyMode.JIAO == moneyMode) {
			_result = singleWin / multiple * 10;
		} else if (MoneyMode.FEN == moneyMode) {
			_result = singleWin / multiple * 100;
		} else {
			log.error("不支持此货币模式,元角模式为" + moneyMode);
		}
		
		return _result;
	}
}
