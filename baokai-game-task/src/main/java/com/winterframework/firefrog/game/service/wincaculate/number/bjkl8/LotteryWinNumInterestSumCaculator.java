package com.winterframework.firefrog.game.service.wincaculate.number.bjkl8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/**
 * 处理北京快乐8的如下彩种：和值大小单双属性
*/
public class LotteryWinNumInterestSumCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	private Map<String, Integer> winToBetTypeMap;

	public Map<String, Integer> getWinToBetTypeMap() {
		return winToBetTypeMap;
	}

	public void setWinToBetTypeMap(Map<String, Integer> winToBetTypeMap) {
		this.winToBetTypeMap = winToBetTypeMap;
	}

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		List<String> resultNumbers = new ArrayList<String>(Arrays.asList(resultCode.split(",")));
		int sumNumber = 0;
		for (String string : resultNumbers) {
			sumNumber += Integer.valueOf(string);
		}
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if (sumNumber > 810) {
			if (sumNumber % 2 != 0) {
				//大单
				if (betDetail.contains("大单")) {
					resultMap.put(winToBetTypeMap.get("大单").toString(), 1);
				}
			} else {
				//大双
				if (betDetail.contains("大双")) {
					resultMap.put(winToBetTypeMap.get("大双").toString(), 1);
				}
			}
		} else {
			if (sumNumber % 2 != 0) {
				//小单
				if (betDetail.contains("小单")) {
					resultMap.put(winToBetTypeMap.get("小单").toString(), 1);
				}
			} else {
				//小双
				if (betDetail.contains("小双")) {
					resultMap.put(winToBetTypeMap.get("小双").toString(), 1);
				}
			}
		}
		return generateWinResultMultBean(resultMap, context);
	}
}
