/**   
* @Title: GameTrendSumValChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 上午11:06:30 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendSumValChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 计算和值
* @date 2014-5-23 上午11:06:30 
*  
*/
public class GameTrendSumValChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.SumVal.getIndex();
		List<Integer> numberRecordList = getNumberRecordList(gdr);

		Long sumVal = 0l;
		for (Integer num : numberRecordList) {
			sumVal += num;
		}
		return this.getGameJbylTrend(String.valueOf(sumVal), null, gdr);
	}

}
