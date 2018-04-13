package com.winterframework.firefrog.game.service.gametrend;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

/** 
* @ClassName: IIGameTrendChartGenerate 
* @Description: 走势图数据生成接口 
* @author Floy 
* @date 2014-4-1 下午3:35:59 
*  
*/
public interface IGameTrendChartGenerate {
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception;
}
