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
	 * 取得彩种ID。
	 * @return
	 */
	public abstract Long getLotteryType();

	/**
	 * 取得玩法群編碼。
	 * @return
	 */
	public abstract Integer getGroupCode();

	/**
	 * 取得玩法组編碼。
	 * @return
	 */
	public abstract Integer getSetCode();

	/**
	 * 取得投注方法編碼。
	 * @return
	 */
	public abstract Integer getMethodCode();

	/**
	 * 取得辅助玩法類型。
	 * @return
	 */
	public abstract Integer getMethodType();
}