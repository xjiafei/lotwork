/**   
* @Title: IIGameTrendChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-1 下午3:35:59 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

/** 
* @ClassName: IIGameTrendChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-1 下午3:35:59 
*  
*/
public interface IGameTrendChartGenerate {
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception;
}
