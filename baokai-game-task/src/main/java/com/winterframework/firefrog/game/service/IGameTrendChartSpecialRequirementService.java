/**   
* @Title: IGameTrendChartSpecialRequirementService.java 
* @Package com.winterframework.firefrog.game.service 
* @Description: winter-game-task.IGameTrendChartSpecialRequirementService.java 
* @author Denny  
* @date 2014-7-30 下午1:55:57 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service;

/** 
* @ClassName: IGameTrendChartSpecialRequirementService 
* @Description: 走势图特殊需求Service接口
* @author Denny 
* @date 2014-7-30 下午1:55:57 
*  
*/
public interface IGameTrendChartSpecialRequirementService {

	/**
	 * 
	* @Title: updateMaxOmitValue 
	* @Description: 更新最大的遗漏值
	* @param   设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateMaxOmitValue(Long lotteryId, String omityTrend) throws Exception;
}
