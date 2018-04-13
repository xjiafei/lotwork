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
public class GameTrendKuaduSsqGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

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
		type = TrendTypeEnum.KuaDu.getIndex();
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
		/*	//红球遗漏
			for (int i = 0; i < 33; i++) {
				if (numberList.contains(i + 1)) {
					lastValues.set(i, 0);
				} else {
					lastValues.set(i, lastValues.get(i) + 1);
				}
			}*/
		//篮球遗漏
		for (int i = 0; i < 16; i++) {
			if (nanMa == (i + 1)) {
				lastValues.set(i, 0);
			} else {
				lastValues.set(i, lastValues.get(i) + 1);
			}
		}
		//单双
		lastValues.set(16, nanMa % 2 != 0 ? 0 : lastValues.get(16) + 1);
		lastValues.set(17, nanMa % 2 == 0 ? 0 : lastValues.get(17) + 1);
		//质合
		lastValues.set(18, isZhihe(nanMa) == false ? 0 : lastValues.get(18) + 1);
		lastValues.set(19, isZhihe(nanMa) == true ? 0 : lastValues.get(19) + 1);
		//大小
		lastValues.set(20, nanMa > 8 ? 0 : lastValues.get(20) + 1);
		lastValues.set(21, nanMa <= 8 ? 0 : lastValues.get(21) + 1);
		//012
		lastValues.set(22, nanMa % 3 == 0 ? 0 : lastValues.get(22) + 1);
		lastValues.set(23, nanMa % 3 == 1 ? 0 : lastValues.get(23) + 1);
		lastValues.set(24, nanMa % 3 == 2 ? 0 : lastValues.get(24) + 1);

		/*小单质：01 03 05 07
		小双质：02
		小双合：04 06 08
		大单质：11 13
		大单合：09 15
		大双合：10 12 14 16*/
		lastValues.set(25, (nanMa == 1 || nanMa == 3 || nanMa == 5 || nanMa == 7) ? 0 : lastValues.get(25) + 1);
		lastValues.set(26, (nanMa == 2) ? 0 : lastValues.get(26) + 1);
		lastValues.set(27, (nanMa == 4 || nanMa == 6 || nanMa == 8) ? 0 : lastValues.get(27) + 1);
		lastValues.set(28, (nanMa == 11 || nanMa == 13) ? 0 : lastValues.get(28) + 1);
		lastValues.set(29, (nanMa == 9 || nanMa == 15) ? 0 : lastValues.get(29) + 1);
		lastValues.set(30, (nanMa == 10 || nanMa == 12 || nanMa == 14 || nanMa == 16) ? 0 : lastValues.get(30) + 1);
		//蓝球四区分布
		lastValues.set(31, (nanMa >= 1 && nanMa <= 4) ? 0 : lastValues.get(31) + 1);
		lastValues.set(32, (nanMa >= 5 && nanMa <= 8) ? 0 : lastValues.get(32) + 1);
		lastValues.set(33, (nanMa >= 9 && nanMa <= 12) ? 0 : lastValues.get(33) + 1);
		lastValues.set(34, (nanMa >= 13 && nanMa <= 16) ? 0 : lastValues.get(34) + 1);

		omission = appendEveryBit(omission, lastValues, SEPERATOR);

		return getGameJbylTrend(omission.substring(0, omission.length() - 1),
				omission.substring(0, omission.length() - 1), gdr);
	}

	private boolean isZhihe(Integer record) {
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
		return isHe;
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
		GameTrendKuaduSsqGenerate s = new GameTrendKuaduSsqGenerate();
		GameDrawResult gdr = new GameDrawResult();
		gdr.setNumberRecord("01,02,03,04,05,07+01");

		GameTrendJbyl ss = s.doGenerateChart(gdr, null);
		System.out.print(ss.getValue());
	}

}
