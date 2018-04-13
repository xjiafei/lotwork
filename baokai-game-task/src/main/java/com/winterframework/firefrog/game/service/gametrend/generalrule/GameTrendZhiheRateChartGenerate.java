/**   
* @Title: GameTrendZhiheRateChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 上午10:46:05 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendZhiheRateChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 质合比遗漏
* @date 2014-5-23 上午10:46:05 
*  
*/
public class GameTrendZhiheRateChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.ZhiHeRate.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		int he = 0;
		for (Integer record : numberRecordList) {
			if (record > 3) {
				for (int i = 2; i < record; i++) {
					if (record % i == 0) {
						he++;
						break;
					}
				}
			} else if (record == 0) {
				he++;
			}
		}
		return getGameJbylTrend(makeNewOmission(lastValue, he), null, gdr);
	}

}
