package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;

/** 
* @ClassName: IGameMutipleService 
* @Description: 投注限制Service接口 
* @author Alan
* @date 2013-8-26 下午1:54:02 
*  
*/
public interface IGameMultipleService {
	
	/**
	 * 
	* @Title: queryMaxMutiple 
	* @Description: 根据条件查询投注最大倍数 
	* @param entity
	* @return
	 */
	public Integer queryMaxMutiple(GameMultipleEntity entity);
	
	/**
	 * 
	* @Title: queryGameMultiple 
	* @Description: 查询GameMultipleEntity
	* @param entity
	* @return GameMultipleEntity
	 */
	public GameMultipleEntity queryGameMultiple(GameMultipleEntity entity);
	
	public List<BetLimitAssist> getGameMultipleAssistParentId(long id) throws Exception;

}
