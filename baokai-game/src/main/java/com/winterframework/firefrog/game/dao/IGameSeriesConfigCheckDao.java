package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameSeriesConfigCheck;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameSeriesConfigCheckDao 
* @Description: 运营参数Check操作Dao
* @author Alan
* @date 2013-9-17 下午6:01:33 
*  
*/
public interface IGameSeriesConfigCheckDao extends BaseDao<GameSeriesConfigCheck> {
	
	/**
	 * 
	* @Title: getGameSeriesConfigByLotteryId 
	* @Description: 根据彩种Id查询运营参数配置信息
	* @param lotteryId
	* @return GameSeriesConfigEntity
	* @throws Exception
	 */
	public GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId);
	
	/**
	 * 
	* @Title: updateGameSeriesConfigCheck 
	* @Description: 更新运营参数信息 
	* @param gameSeriesConfig
	 * @throws Exception 
	 */
	public void updateGameSeriesConfigCheck(GameSeriesConfigEntity gameSeriesConfig) throws Exception;
	
	/**
	 * 
	* @Title: deleteGameSeriesConfigCheck 
	* @Description: 删除彩种运营参数信息
	* @param lotteryId
	 */
	public void deleteGameSeriesConfigCheck(Long lotteryId);

}
