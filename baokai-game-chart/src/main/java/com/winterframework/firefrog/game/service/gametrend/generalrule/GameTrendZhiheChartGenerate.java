/**   
* @Title: GameTrendZhiheChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 下午1:25:59 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

/** 
* @ClassName: GameTrendZhiheChartGenerate 
* @Description: 质合形态
* @author 你的名字 
* @date 2014-5-23 下午1:25:59 
*  
*/
public class GameTrendZhiheChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.ZhiHe.getIndex();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);

		StringBuffer sb = new StringBuffer();
		StringBuffer value = new StringBuffer();
		for (Integer record : numberRecordList) {
			boolean isHe = false;
			if (record > 3) {
				for (int i = 2; i < record; i++) {
					if (record % i == 0) {
						isHe = true;
						break;
					}
				}
			} else if (record == 0) {
				isHe = true;
			}
			if (isHe) {
				sb.append("合");
				value.append("0,");
			} else {
				sb.append("质");
				value.append("1,");
			}
		}
		return this.getGameTrendJbyl(value.toString().substring(0, value.toString().length() - 1), sb.toString(), gdr);
	}

}
