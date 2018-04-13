package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameTrendJbylDao 
* @Description: 号码走势分析表DAO
* @author Denny
* @date 2014-4-1 下午4:14:32 
*
 */
public interface IGameTrendJbylDao extends BaseDao<GameTrendJbyl> {
	
	/**
	 * 
	* @Title: getTrendJbyl 
	* @Description: 查询遗漏数据
	* @param   lotteryId
	* @param   gameGroupCode
	* @param   gameSetCode
	* @param   trendType
	* @param   num 
	* @return List<GameTrendJbyl>    返回类型 
	* @throws
	 */
	public List<GameTrendJbyl> getTrendJbyl(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode, Integer trendType, int num);

	/** 
	* @Title: getTrendByBetMethod 
	* @Description: (这里用一句话描述这个方法的作用) 
	* @param   设定文件 
	* @return List<GameTrendJbyl>    返回类型 
	* @throws 
	*/
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num);
	
}
