/**   
* @Title: IGameTrendService.java 
* @Package com.winterframework.firefrog.game.service 
* @Description: winter-game.IGameTrendJbylService.java 
* @author Denny  
* @date 2014-3-27 下午1:45:58 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import java.util.Map;
import com.winterframework.firefrog.game.entity.TrendType;

/** 
* @ClassName: IGameTrendService 
* @Description: 走势图数据Service接口 
* @author Denny 
* @date 2014-3-27 下午1:45:58 
*  
*/
public interface IGameTrendService {
	
	/**
	* Title: getTrendByBetMethod
	* Description:查询彩种统计数据
	* @param lotteryId
	* @param gameGroupCode
	* @param gameSetCode
	* @param betMethodCode
	* @param num
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameTrendService#getTrendByBetMethod(long, int, int, int, int) 
	*/
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num);

	/**
	 * 
	* @Title: queryOmissionValue 
	* @Description: 查询遗漏数据
	* @param  lotteryId
	* @param  gameGroupCode
	* @param  num	期数
	* @return Map<Integer,String>    返回类型 
	* @throws
	 */
	public Map<TrendType, List<String>> queryOmissionValue(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode, Integer num);
}
