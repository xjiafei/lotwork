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

import javax.print.attribute.standard.MediaSize.ISO;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;
import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/**
 * 和值组合形态遗漏
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月11日
 */
public class GameTrendHezhizuheChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		//小奇 小偶  大奇 大偶 
		//0  1  2  3
		type = TrendTypeEnum.HeZhiZuHe.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		int curValue = 0;
		int sumValue=0;
		for (Integer number : numberRecordList) {
			sumValue+=number; 
		} 
		if(isLittle(sumValue) && isOdd(sumValue)){
			curValue=0;
		}else if(isLittle(sumValue) && !isOdd(sumValue)){
			curValue=1;
		}else if(!isLittle(sumValue) && isOdd(sumValue)){
			curValue=2;
		}else{
			curValue=3;
		}
		String value=makeNewOmission(lastValue, curValue);
		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(value); 
		return getGameJbylTrend(value, makeSingleOmissionStruc(omissionValues), gdr);
	}
	
	protected boolean isLittle(int number){
		return number<=10;
	}
	protected boolean isOdd(int number){
		return number%2!=0;
	}

}
