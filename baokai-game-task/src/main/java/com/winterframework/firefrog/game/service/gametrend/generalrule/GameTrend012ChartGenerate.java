/**   
* @Title: GameTrend012ChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 下午1:51:58 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrend012ChartGenerate 
* @Description: 012形态 
* @author 你的名字 
* @date 2014-5-23 下午1:51:58 
*  
*/
public class GameTrend012ChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	/**
	* Title: doGenerateChart
	* Description:
	* @param gdr
	* @param list
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.Lin12.getIndex();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		StringBuffer value = new StringBuffer();
		for (Integer record : numberRecordList) {
			int result = record % 3;
			value.append(result + ",");
		}
		return this.getGameJbylTrend(value.toString().substring(0, value.toString().length() - 1), null, gdr);
	}

}
