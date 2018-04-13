/**   
* @Title: GameTrendWeishuSsqGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-7 下午3:58:46 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;
import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/** 
* @ClassName: GameTrendWeishuSsqGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-7 下午3:58:46 
*  
*/
public class GameTrendWeishuSsqGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.WeiShu.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> lastValues = NumberTrendsUtils.explode(lastValue);
		List<String> result = explode(gdr.getNumberRecord());
		StringBuffer omission = new StringBuffer();
		if (!result.isEmpty()) {
			String last = result.get(result.size() - 1);
			List<String> code = Arrays.asList(last.split("\\+"));
			result.remove(result.size() - 1);
			result.addAll(code);
		}
		Integer nanMa = Integer.valueOf(result.remove(result.size() - 1));
		List<Integer> numberList = new ArrayList<Integer>();
		for (String num : result) {
			numberList.add(Integer.valueOf(num));
		}
		//红球遗漏
		for (int i = 0; i < 33; i++) {
			if (numberList.contains(i + 1)) {
				lastValues.set(i, 0);
			} else {
				lastValues.set(i, lastValues.get(i) + 1);
			}
		}
		//篮球遗漏
		for (int i = 0; i < 16; i++) {
			if (nanMa == (i + 1)) {
				lastValues.set(33 + i, 0);
			} else {
				lastValues.set(33 + i, lastValues.get(33 + i) + 1);
			}
		}
		omission = appendEveryBit(omission, lastValues, SEPERATOR);

		return getGameJbylTrend(omission.substring(0, omission.length() - 1),
				omission.substring(0, omission.length() - 1), gdr);
	}

	/** 
	* @Title: appendEveryBit 
	* @Description: 连接数据
	*/
	private StringBuffer appendEveryBit(StringBuffer omission, List<Integer> bitNumber, String seperator) {
		for (int num : bitNumber) {
			omission.append(num);
			omission.append(seperator);
		}
		return omission;
	}

	/** 
	* @Title: explode 
	* @Description: 将中奖号码String转换为int型的List 
	*/
	public List<String> explode(String s) {
		List<String> list = new ArrayList<String>();
		if (s != null) {
			if (s.contains(",")) {
				String[] recordArray = s.split(",");
				for (String record : recordArray) {
					list.add(record);
				}

			} else {
				for (int i = 0; i < s.length(); i++) {
					list.add(String.valueOf(s.charAt(i)));
				}
			}
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		GameTrendWeishuSsqGenerate s = new GameTrendWeishuSsqGenerate();
		GameDrawResult gdr = new GameDrawResult();
		gdr.setNumberRecord("01,02,03,04,05,07+01");
		s.doGenerateChart(gdr, null);
	}

}
