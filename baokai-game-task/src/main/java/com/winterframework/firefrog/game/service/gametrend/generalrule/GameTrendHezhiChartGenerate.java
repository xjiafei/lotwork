/**   
* @Title: GameTrendFenbuChartGenerate.java 
* @Package GameTrendWeishuChartGenerate 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:35:12 
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
* @ClassName: GameTrendFenbuChartGenerate 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-2 下午3:35:12 
*  
*/
public class GameTrendHezhiChartGenerate extends BaseGeneralRule implements IGameTrendChartGenerate {

	/**
	* Title: doGenerateChart
	* Description:和值遗漏数据统计
	* @param gdr
	* @param list
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate#doGenerateChart(com.winterframework.firefrog.game.dao.vo.GameDrawResult, java.util.List) 
	*/
	@Override
	public GameTrendJbyl doGenerateChart(GameDrawResult gdr, List<GameTrendJbyl> list) throws Exception {
		type = TrendTypeEnum.HeZhi.getIndex();
		String lastValue = this.getLastGameJbylTrend(list).getValue();
		List<Integer> numberRecordList = this.getNumberRecordList(gdr);
		int sumValue = 0;
		for (Integer hezhi : numberRecordList) {
			sumValue += hezhi;
		}
		String value = makeNewOmission(lastValue, sumValue);
		List<Integer> omissionValues = NumberTrendsUtils.convertString2List(value);
		return getGameJbylTrend(value, makeSingleOmissionStruc(omissionValues), gdr);
	}

	/** 
	* @Title: makeSingleOmissionStruc 
	* @Description:  生成单个遗漏值数据体 
	* @param @param omissionValues
	* @param @param heZhiRenges
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
		if(this.setCode == 11){
			String [] singleArray = singleOmissionStruc.split(ServiceConstance.groupSeparate);
			sb = new StringBuffer();
			for(int i=1;i<singleArray.length -1;i++){
				sb.append(singleArray[i]).append(ServiceConstance.groupSeparate);
			}
			return sb.toString().substring(0, sb.toString().length() - 1);
		}

		return singleOmissionStruc;
	}

}
