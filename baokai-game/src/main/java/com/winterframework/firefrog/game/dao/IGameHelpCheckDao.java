package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameHelpCheck;

/** 
* @ClassName: IGameHelpCheckDao 
* @Description: 玩法描述审核DAO接口 
* @author Denny 
* @date 2013-8-25 下午10:13:18 
*  
*/
public interface IGameHelpCheckDao {
	/**
	* @Title: getGameHelpCheckByLotteryId 
	* @Description: 查询玩法描述审核
	 */
	public List<GameHelpCheck> getGameHelpCheckByLotteryId(long lotteryid);

	/**
	* @Title: addGameHelpCheck 
	* @Description: 添加玩法描述审核
	 */
	public void addGameHelpCheck(long lotteryid);

	/**
	* @Title: removeGameHelpCheck 
	* @Description: 删除玩法描述审核
	 */
	public void removeGameHelpCheck(long id);

	/**
	* @Title: updateGameHelpCheck 
	* @Description: 修改玩法描述审核
	 */
	public void updateGameHelpCheck(GameHelpCheck GameHelpCheck);
	
	public void insertAll(List<GameHelpCheck> entitys);
	
	public void checkGameHelpCheck(Long lotteryid, Long auditType);

	public void removeGameHelpCheckByLotteryId(Long lotteryid);

	public void updateToCheck(GameHelpCheck g);
}
