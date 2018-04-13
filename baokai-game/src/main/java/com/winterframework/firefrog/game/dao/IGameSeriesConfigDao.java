package com.winterframework.firefrog.game.dao;

import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameSeriesConfig;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameSeriesConfigDao 
* @Description: 运营参数操作Dao 
* @author Alan
* @date 2013-12-27 下午4:17:14 
*
 */
public interface IGameSeriesConfigDao extends BaseDao<GameSeriesConfig> {

	/**
	 * 
	* @Title: getGameSeriesConfigByLotteryId 
	* @Description:根据LotteryId获取GameSeriesConfig信息 
	* @param lotteryId
	* @return GameSeriesConfigEntity
	* @throws Exception
	 */
	public GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) throws Exception;

	public void updateVideoStruc(Map<String, Object> map);
	
}
