/**   
* @Title: GameTrendDaXiaoRateChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-23 上午9:43:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;
import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/**
 * 快三号码形态遗漏
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月11日
 */
public class GameTrendHaomaxingtaiChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		//号码形态 :三同号 三不同号 三连号 二同号（复）二同号（单）二不同号 
		//       0    1    2    3       4         5
		type = TrendTypeEnum.HaoMaXingTai.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		String[] lastValueArr = lastValue.split(SEPERATOR);
		List<Integer> numberRecordList = this.getNumberRecordList(gdr); 
		StringBuilder current = new StringBuilder();
		current.append(GameTrendK3Util.isThreeSame(numberRecordList)?"0":Integer.parseInt(lastValueArr[0])+1).append(",");
		current.append(GameTrendK3Util.isThreeDiff(numberRecordList)?"0":Integer.parseInt(lastValueArr[1])+1).append(",");
		current.append(GameTrendK3Util.isThreeConsecutive(numberRecordList)?"0":Integer.parseInt(lastValueArr[2])+1).append(",");
		current.append(GameTrendK3Util.isTwoSameMulty(numberRecordList)?"0":Integer.parseInt(lastValueArr[3])+1).append(",");
		current.append(GameTrendK3Util.isTwoSameSingle(numberRecordList)?"0":Integer.parseInt(lastValueArr[4])+1).append(",");
		current.append(GameTrendK3Util.isTwoDiff(numberRecordList)?"0":Integer.parseInt(lastValueArr[5])+1).append(",");
		String value=current.substring(0, current.length() - 1);
		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(value); 
		return getGameJbylTrend(value, makeSingleOmissionStruc(omissionValues), gdr);
	}
}
