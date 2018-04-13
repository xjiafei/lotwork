/**   
* @Title: GameTrendFenbuChartGenerate.java 
* @Package GameTrendWeishuChartGenerate 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:35:12 
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
* @ClassName: GameTrendFenbuChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-2 下午3:35:12 
*  
*/
public class GameTrendFenbuChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	/**
	* Title: doGenerateChart
	* Description:分布遗漏数据统计
	* @param gdr
	* @param list
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.FenBu.getIndex();
		String lastValue = this.getLastGameTrendJbyl(list).getValue();
		Set<Integer> numberRecordSet = new HashSet<Integer>();
		numberRecordSet.addAll(this.getNumberRecordList(gdr));
		StringBuilder omissionNumberPublish = new StringBuilder();

		String[] arr = lastValue.split(SEPERATOR);

		int j = startValue == null ? 0 : startValue;
		for (int i = 0; i < arr.length; i++) {
			Integer n = Integer.parseInt(arr[i]);
			if (numberRecordSet.contains(j)) {
				omissionNumberPublish.append("0,");
			} else {
				n++;
				omissionNumberPublish.append(n).append(",");
			}
			j++;
		}
		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(omissionNumberPublish.substring(0,
				omissionNumberPublish.length() - 1));
		return getGameTrendJbyl(omissionNumberPublish.substring(0, omissionNumberPublish.length() - 1),
				makeSingleOmissionStruc(omissionValues, this.getNumberRecordList(gdr)), gdr);
	}

	/** 
	* @Title: makeSingleOmissionStruc 
	* @Description: 生成单个遗漏值数据体 
	* @param omissionValues
	* @param numberRecordRange
	* @param numberRecords
	* @return String    返回类型 
	* @throws 
	*/
	private String makeSingleOmissionStruc(List<Integer> omissionValues, List<Integer> numberRecords) {

		StringBuffer sb = new StringBuffer();
		int j = startValue == null ? 0 : startValue;
		for (int i = 0; i < omissionValues.size(); i++) {
			sb.append(j);
			j++;
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i));
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i) == 0 ? 1 : 0);
			sb.append(ServiceConstance.paraSeparate);
			sb.append(isMultiple(i, numberRecords) ? 1 : 0);
			sb.append(ServiceConstance.groupSeparate);
		}

		String singleOmissionStruc = sb.toString();
		singleOmissionStruc = singleOmissionStruc.substring(0, singleOmissionStruc.length() - 1);

		return singleOmissionStruc;
	}

	/** 
	* @Title: isMultiple 
	* @Description: 是否重号
	* @param ballNumber
	* @param numberRecords
	* @param numberRecordRange
	* @return boolean    返回类型 
	* @throws 
	*/
	private boolean isMultiple(int ballNumber, List<Integer> numberRecords) {

		boolean flag = false;

		int count = 0;
		for (int number : numberRecords) {
			if (ballNumber == number) {
				count++;
			}
		}

		if (count > 1) {
			flag = true;
		}

		return flag;
	}

}
