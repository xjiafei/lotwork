/**   
* @Title: IGameMultipleDao.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: 投注限制倍数DAO接口
* @author Denny  
* @date 2013-8-20 下午4:40:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameMultiple;
import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;

/** 
* @ClassName: IGameMultipleDao 
* @Description: 投注限制倍数DAO接口
* @author Denny 
* @date 2013-8-20 下午4:40:41 
*  
*/
public interface IGameMultipleDao {

	/**
	* @Title: getGameMultiple 
	* @Description: 根据彩种查询投注限制
	 */
	public List<GameMultiple> getGameMultipleByLotteryId(long lotteryid);

	/**
	* @Title: addGameMultiple 
	* @Description: 添加投注限制
	 */
	public void addGameMultiple(long lotteryid);

	/**
	* @Title: removeGameMultiple 
	* @Description: 根据彩种删除投注限制
	 */
	public void removeGameMultipleByLotteryId(long lotteryid);

	/**
	* @Title: updateGameMultiple 
	* @Description: 修改投注限制
	 */
	public void updateGameMultiple(GameMultiple gameMultiple);

	/**
	 * 
	* @Title: queryGameMutiple 
	* @Description: 根据字段得到投注限制信息
	* @param entity
	* @return
	 */
	public GameMultipleEntity queryGameMultiple(GameMultipleEntity gameMultiple);

	public void updateGameMultipleList(List<GameMultiple> gameMultipleList);

	public List<BetLimitAssist> getGameMultipleAssistParentId(long id) throws Exception;
	
	public void modifyBetLimitAssist(BetLimitAssist assist) throws Exception;
}
