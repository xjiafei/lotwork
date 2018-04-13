package com.winterframework.firefrog.game.service.api;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

/**
 * 走势图对外服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月17日
 */
public interface IGameTrendService { 
	/**
	 * 获取走势数据-遗漏
	 * @param lotteryId
	 * @param userId
	 * @param gameGroupCode
	 * @param trendType
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	List<GameTrendJbyl> getTrendJbyl(Long lotteryId,Long userId,Integer gameGroupCode,Integer trendType,Date startTime,Date endTime,Integer top) throws Exception;
	
}
