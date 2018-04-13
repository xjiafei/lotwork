package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;

 
/**
 * 投注方式状态服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年9月28日
 */
public interface IGameBetTypeStatusService {

	/**
	 * 获取投注方式状态表(根据彩种和玩法）
	 * @param lotteryId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	public GameBettypeStatus getGameBetTypeStatusByLotteryIdAndBetTypeCode(Long lotteryId,String betTypeCode) throws Exception;
	
	/**
	 * 获取玩法别名
	 * @param lotteryId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	public String getAlias(Long lotteryId,String betTypeCode) throws Exception;
}
