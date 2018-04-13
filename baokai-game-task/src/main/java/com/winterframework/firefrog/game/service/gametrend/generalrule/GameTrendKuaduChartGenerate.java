/**   
* @Title: GameTrendKuaduChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:54:36 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.common.constance.ServiceConstance;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;
import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/** 
* @ClassName: GameTrendKuaduChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-2 下午3:54:36 
*  
*/
public class GameTrendKuaduChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	/**
	* Title: doGenerateChart
	* Description:跨度遗漏数据统计
	* @param gdr
	* @param list
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.KuaDu.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		Set<Integer> numberRecordSet = new HashSet<Integer>();
		numberRecordSet.addAll(this.getNumberRecordList(gdr));
		int kdValue = NumberTrendsUtils.getKd(numberRecordSet);
		String value = makeNewOmission(lastValue, kdValue);
		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(value);
		return getGameJbylTrend(value, makeSingleOmissionStruc(omissionValues), gdr);
	}

	/** 
	* @Title: makeSingleOmissionStruc 
	* @Description: 生成单个遗漏值数据体 
	* @param @param omissionValues
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	protected String makeSingleOmissionStruc(List<Integer> omissionValues) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < omissionValues.size(); i++) {
			sb.append(i);
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i));
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i) == 0 ? 1 : 0);
			sb.append(ServiceConstance.groupSeparate);
		}

		String singleOmissionStruc = sb.toString();
		singleOmissionStruc = singleOmissionStruc.substring(0, singleOmissionStruc.length() - 1);

		return singleOmissionStruc;
	}
}
