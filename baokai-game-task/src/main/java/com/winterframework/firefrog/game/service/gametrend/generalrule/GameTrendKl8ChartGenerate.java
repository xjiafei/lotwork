/**   
* @Title: GameTrendKl8ChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-4 下午3:13:20 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.common.constance.ServiceConstance;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;
import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/** 
* @ClassName: GameTrendKl8ChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-4 下午3:13:20 
*  
*/
public class GameTrendKl8ChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	private static final long CHECK_DA_NUMBER = 810l;

	/**
	* Title: doGenerateChart
	* Description:统计快乐8辅助图数据
	* @param gdr
	* @param list
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.KL8.getIndex();
		List<Integer> numberRecordList = NumberTrendsUtils.explode(gdr.getNumberRecord());
		Long sum = heZhiCaculate(numberRecordList);
		String value = shangXiaCaculate(numberRecordList) + "," + jiOuCaculate(numberRecordList) + ","
				+ daXiaoCaculate(sum) + "," + danSuangCaculate(sum) + "," + sum;
		return this.getGameJbylTrend(value, null, gdr);
	}

	private String shangXiaCaculate(List<Integer> numberRecordList) {
		int upCount = 0, downCount = 0;
		for (Integer record : numberRecordList) {
			if (record >= 1 && record <= 40) {
				upCount++;
			} else {
				downCount++;
			}
		}
		return upCount > downCount ? ServiceConstance.SHANG : upCount == downCount ? ServiceConstance.ZHONG
				: ServiceConstance.XIA;
	}

	private String jiOuCaculate(List<Integer> numberRecordList) {
		int jiCount = 0, ouCount = 0;
		for (Integer record : numberRecordList) {
			if (record % 2 == 0) {
				ouCount++;
			} else {
				jiCount++;
			}
		}
		return jiCount > ouCount ? ServiceConstance.JI : jiCount == ouCount ? ServiceConstance.HE : ServiceConstance.OU;
	}

	private Long heZhiCaculate(List<Integer> numberRecordList) {
		Long sum = 0l;
		for (Integer record : numberRecordList) {
			sum += record;
		}
		return sum;
	}

	private String daXiaoCaculate(Long sum) {
		return sum > CHECK_DA_NUMBER ? ServiceConstance.DA : ServiceConstance.XIAO;
	}

	private String danSuangCaculate(Long sum) {
		return sum % 2 == 0 ? ServiceConstance.SHUANG : ServiceConstance.DAN;
	}

}
