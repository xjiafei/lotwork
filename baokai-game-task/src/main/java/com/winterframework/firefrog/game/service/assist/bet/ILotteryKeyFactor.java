package com.winterframework.firefrog.game.service.assist.bet;

/** 
* @ClassName: ILotteryKeyFactor 
* @Description:  
* @author page
* @date 2013-8-6 下午12:01:08 
*  
*/
public interface ILotteryKeyFactor {
	/**
	 * 彩种
	 */
	public abstract Long getLotteryType();

	/**
	 * 玩法群
	 */
	public abstract Integer getGroupCode();

	/**
	 * 玩法组
	 */
	public abstract Integer getSetCode();

	/**
	 * 玩法
	 */
	public abstract Integer getMethodCode();

	/**
	 * 辅助玩法
	 */
	public abstract Integer getMethodType();
}