/**   
* @Title: GameTrendDanShuangChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-21 下午4:24:13 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendDanShuangChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-21 下午4:24:13 
*  
*/
public class GameTrendDanShuangChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		this.type = TrendTypeEnum.DanShuang.getIndex();
		List<Integer> resultList = this.getNumberRecordList(gdr);
		int dan = 0, shuang = 0;
		StringBuffer value = new StringBuffer();
		for (Integer num : resultList) {
			if (num % 2 == 0) {
				shuang++;
				value.append("0,");
			} else {
				dan++;
				value.append("1,");
			}
		}
		return this.getGameTrendJbyl(value.toString().substring(0, value.toString().length() - 1), dan + ":" + shuang,
				gdr);
	}
}
