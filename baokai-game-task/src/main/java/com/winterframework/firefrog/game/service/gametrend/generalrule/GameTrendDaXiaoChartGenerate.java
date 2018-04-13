/**   
* @Title: GameTrendDaXiaoChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-22 下午3:34:37 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendDaXiaoChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-22 下午3:34:37 
*  
*/
public class GameTrendDaXiaoChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.DaXiao.getIndex();
		List<Integer> numberRecordList = getNumberRecordList(gdr);
		StringBuffer sb = new StringBuffer();
		StringBuffer value = new StringBuffer();
		for (Integer num : numberRecordList) {
			if (num > 4) {
				sb.append("大");
				value.append("1,");
			} else {
				sb.append("小");
				value.append("0,");
			}
		}
		return this.getGameJbylTrend(value.toString().substring(0, value.toString().length() - 1), sb.toString(), gdr);
	}

}
