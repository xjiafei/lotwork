/**   
* @Title: IGameBonusPoolService.java 
* @Package com.winterframework.firefrog.game.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午3:23:53 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameBonusPool;

/** 
* @ClassName: IGameBonusPoolService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午3:23:53 
*  
*/
public interface IGameBonusPoolService {

	public GameBonusPool queryGameBonus(Long lotteryId) throws Exception;

	public int updateGameBonusPool(GameBonusPool gameBonusPool) throws Exception;
}
