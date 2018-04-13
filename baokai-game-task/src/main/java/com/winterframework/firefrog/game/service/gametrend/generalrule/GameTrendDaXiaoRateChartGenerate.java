/**   
* @Title: GameTrendDaXiaoRateChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 上午9:43:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendDaXiaoRateChartGenerate 
* @Description: 大小比遗漏
* @author 你的名字 
* @date 2014-5-23 上午9:43:41 
*  
*/
public class GameTrendDaXiaoRateChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.DaXiaoRate.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		int xiao = 0;
		for (Integer record : numberRecordList) {
			if (record <= 4) {
				xiao++;
			}
		}
		return getGameJbylTrend(makeNewOmission(lastValue, xiao), null, gdr);
	}

}
