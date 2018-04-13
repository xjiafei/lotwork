/**   
* @Title: GameTrendKuaduChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:54:36 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class GameTrendZuxuanChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	private static Map<Integer, Integer[]> ZUXUANMAP = new HashMap<Integer, Integer[]>();

	static {
		ZUXUANMAP.put(10, new Integer[] { 43, 44, 45, 46, 47, 48 });
		ZUXUANMAP.put(11, new Integer[] { 49, 50, 51, 52 });
		ZUXUANMAP.put(12, new Integer[] { 35, 36, 38 });
		ZUXUANMAP.put(13, new Integer[] { 35, 36, 38 });
		ZUXUANMAP.put(333, new Integer[] { 35, 36, 38 });	//中三
	}

	/**
	* Title: doGenerateChart
	* Description:组选遗漏数据统计
	* @param gdr
	* @param list
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.ZuXuan.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		String subNumberRecord = "";
		for (Integer num : numberRecordList) {
			subNumberRecord += num;
		}

		Integer[] zuXuanBetMethodCode = ZUXUANMAP.get(this.groupCode);
		String value = null;
		if (zuXuanBetMethodCode != null) {
			int betMethodCode = this.groupCode.longValue() == 10l ? NumberTrendsUtils.wuxingZuXuan(subNumberRecord)
					: this.groupCode.longValue() == 11l ? NumberTrendsUtils.sixingZuXuan(subNumberRecord)
							:(this.groupCode.longValue() == 12l || this.groupCode.longValue() == 13l || this.groupCode.longValue() == 333l)? NumberTrendsUtils
									.zushan(subNumberRecord) : -1;
			int betMethodIndex = -1;
			for (int i = 0; i < zuXuanBetMethodCode.length; i++) {
				if (betMethodCode == zuXuanBetMethodCode[i]) {
					betMethodIndex = i;
					break;
				}
			}
			value = makeNewOmission(lastValue, betMethodIndex);
		} else {
			value = numberRecordList.get(0).intValue() == numberRecordList.get(1).intValue() ? "0" : String
					.valueOf(Integer.valueOf(lastValue) + 1);
			zuXuanBetMethodCode = new Integer[] { 64 };

		}

		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(value);
		return getGameJbylTrend(value, makeSingleOmissionStruc(omissionValues, zuXuanBetMethodCode), gdr);
	}

	/** 
	* @Title: makeSingleOmissionStruc 
	* @Description: 生成单个遗漏值数据体 
	* @param omissionValues
	* @param zuXuanTypes
	* @return String    返回类型 
	* @throws 
	*/
	private String makeSingleOmissionStruc(List<Integer> omissionValues, Integer[] zuXuanTypes) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < omissionValues.size(); i++) {

			sb.append(zuXuanTypes[i]);
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
