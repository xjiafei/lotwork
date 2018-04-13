/**   
* @Title: GameTrendWeishuChartGenerate.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 上午11:57:46 
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
* @ClassName: GameTrendWeishuChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2014-4-2 上午11:57:46 
*  
*/
public class GameTrendWeishuChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	private String gameGroupNumberBits;

	private Integer digitsEndNumber;

	public void setDigitsEndNumber(Integer digitsEndNumber) {
		this.digitsEndNumber = digitsEndNumber;
	}

	public void setGameGroupNumberBits(String gameGroupNumberBits) {
		this.gameGroupNumberBits = gameGroupNumberBits;
	}

	/**
	* Title: doGenerateChart
	* Description:位数遗漏数据统计
	* @param gdr
	* @param list
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.WeiShu.getIndex();
		String lastValue = this.getLastGameTrendJbyl(list).getValue();
		List<Integer> numberRecordList = getNumberRecordList(gdr);
		StringBuffer omission = new StringBuffer();
		int j = startValue == null ? 0 : startValue;
		for (int i = 0; i < numberRecordList.size(); i++) {
			if (lastValue.split(",").length > i * (digitsEndNumber - j + 1)) {
				Integer[] lastResult = NumberTrendsUtils.convert2IntArray(lastValue, i * (digitsEndNumber - j + 1),
						(digitsEndNumber - j + 1));
				lastResult = makeOmissionDataForEveryBit(lastResult, numberRecordList, i);
				omission = appendEveryBit(omission, lastResult, SEPERATOR);
			}
		}
		List<Integer> bits = NumberTrendsUtils.convertString2List(gameGroupNumberBits);
		List<Integer> omissionValues = NumberTrendsUtils
				.convertString2List(omission.substring(0, omission.length() - 1));
		return getGameTrendJbyl(omission.substring(0, omission.length() - 1),
				makeSingleOmissionStruc(omissionValues, bits, numberRecordList), gdr);
	}

	/** 
	* @Title: appendEveryBit 
	* @Description: 连接数据
	*/
	private StringBuffer appendEveryBit(StringBuffer omission, Integer[] bitNumber, String seperator) {
		for (int num : bitNumber) {
			omission.append(num);
			omission.append(seperator);
		}
		return omission;
	}

	/** 
	* @Title: makeOmissionData 
	* @Description: 
	*/
	private Integer[] makeOmissionDataForEveryBit(Integer[] bitNumber, List<Integer> numberRecordList, int index) {
		int j = startValue == null ? 0 : startValue;
		for (int i = 0; i < bitNumber.length; i++) {
			if (numberRecordList.get(index) == j) {
				bitNumber[i] = 0;
			} else {
				bitNumber[i]++;
			}
			j++;
		}
		return bitNumber;
	}

	/** 
	* @Title: makeSingleOmissionStruc 
	* @Description: 生成单个遗漏值数据体 
	* @param  omissionValues
	* @param  ballStartIndex
	* @param  ballEndIndex
	* @return BaseChartStruc    返回类型 
	* @throws 
	*/
	private String makeSingleOmissionStruc(List<Integer> omissionValues, List<Integer> bits,
			List<Integer> numberRecordList) {

		StringBuffer sb = new StringBuffer();
		int count = -1;
		int j = startValue == null ? 0 : startValue;
		for (int i = 0; i < omissionValues.size(); i++) {
			if (i % (digitsEndNumber - j + 1) == 0) {
				count++;
			}
			sb.append(bits.get(count));
			sb.append(ServiceConstance.paraSeparate);
			sb.append((i % (digitsEndNumber - j + 1)) + j);
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i));
			sb.append(ServiceConstance.paraSeparate);
			sb.append(omissionValues.get(i) == 0 ? 1 : 0);
			sb.append(ServiceConstance.paraSeparate);
			if (methodCode != null && methodCode == count + 1
					&& numberRecordList.get(count) == ((i % (digitsEndNumber - j + 1)) + j)) {
				sb.append(1);
			} else {
				sb.append(0);
			}
			sb.append(ServiceConstance.groupSeparate);
		}

		String singleOmissionStruc = sb.toString();
		singleOmissionStruc = singleOmissionStruc.substring(0, singleOmissionStruc.length() - 1);

		return singleOmissionStruc;
	}

}
