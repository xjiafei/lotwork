package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameHelpAndBonus;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameHelpDao extends BaseDao<GameHelp> {

	/**
	 * 
	* @Title: getGameHelpByLotteryId 
	* @Description: 获取游戏帮助 
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameHelp> getGameHelpByLotteryId(Long lotteryId) throws Exception;
	
	/**
	* @Title: getGameHelp 
	* @Description: 查询玩法描述
	 */
	public List<GameHelp> getGameHelp(long lotteryid);

	/**
	* @Title: addGameHelp 
	* @Description: 添加玩法描述
	 */
	public void addGameHelp(long lotteryid);
	
	/**
	* @Title: removeGameHelp 
	* @Description: 删除玩法描述
	 */
	public void removeGameHelp(long lotteryid);
	
	/**
	* @Title: updateGameHelp 
	* @Description: 修改玩法描述
	 */
	public void updateGameHelp(GameHelp gameHelp);

	public void updateGameHelpList(List<GameHelp> gameHelpList);

	/** 
	* @Title: getByBetMethod 
	* @Description: 按投注方式查询玩法描述 
	*/
	public GameHelp getByBetMethod(long lotteryid, int gameGroupCode, int gameSetCode, int betMethodCode);
	
	/** 
	* @Title: getByBetMethod 
	* @Description: 按投注方式查询玩法描述 
	*/
	public GameHelpAndBonus getByBetMethod(long lotteryid, long userid,int gameGroupCode, int gameSetCode, int betMethodCode);
	
	/**
	 * @Title: getTipsAndGameName 
	 * @Description: 按彩種取得描述與玩法名稱
	 */
	public List<GameHelpAndBonus> getTipsAndGameName(long lotteryid);
}
