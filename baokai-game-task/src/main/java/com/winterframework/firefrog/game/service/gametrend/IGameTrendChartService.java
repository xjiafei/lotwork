package com.winterframework.firefrog.game.service.gametrend;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/** 
* @ClassName: IGameTrendChartService 
* @Description: 走势图数据操作Service接口 
* @author Denny 
* @date 2013-10-25 下午3:59:40 
*  
*/
public interface IGameTrendChartService {

	/** 
	* @Title: generateTrendChartData 
	* @Description: 生成走势图数据
	*/
	public void generateTrendChartData() throws Exception;

	/** 
	* @Title: regenerateTrendChartData 
	* @Description: 重新生成走势图数据 
	*/
	public void regenerateTrendChartData(GameControlEvent event) throws Exception;
	void generateTrendData(GameControlEvent event) throws Exception;
	void generateCurrentIssueTrendData(Long lotteryId, Long issueCode,Long userId) throws Exception;

}
