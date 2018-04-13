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
 * 处理北京快乐8的如下彩种：奇偶盘
*/
public class LotteryWinNumInterestOddEvenCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	private Map<String, String> winToBetTypeMap;

	public Map<String, String> getWinToBetTypeMap() {
		return winToBetTypeMap;
	}

	public void setWinToBetTypeMap(Map<String, String> winToBetTypeMap) {
		this.winToBetTypeMap = winToBetTypeMap;
	}

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		List<String> resultNumbers = new ArrayList<String>(Arrays.asList(resultCode.split(",")));
		int oddNumber = 0;
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		for (String string : resultNumbers) {
			oddNumber += Integer.valueOf(string) % 2;
		}
		if (oddNumber < 10) {
			//偶盘
			if (betDetail.contains("偶")) {
				resultMap.put(winToBetTypeMap.get("偶").toString(), 1);
			}
		} else if (oddNumber == 10) {
			//和盘
			if (betDetail.contains("和")) {
				resultMap.put(winToBetTypeMap.get("和").toString(), 1);
			}
		} else {
			//奇盘
			if (betDetail.contains("奇")) {
				resultMap.put(winToBetTypeMap.get("奇").toString(), 1);
			}
		}
		
		int sumNumber = 0;
		for (String string : resultNumbers) {
			sumNumber += Integer.valueOf(string);
		}
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
		
		int upNumber = 0;
		int downNumber = 0;
		for (String string : resultNumbers) {
			if (Integer.valueOf(string) < 41) {
				upNumber += 1;
			} else {
				downNumber += 1;
			}
		}
		if (upNumber > downNumber) {
			if (betDetail.contains("上")) {
				//上盘
				resultMap.put(winToBetTypeMap.get("上").toString(), 1);
			}
		} else if (upNumber == downNumber) {
			if (betDetail.contains("中")) {
				//中盘
				resultMap.put(winToBetTypeMap.get("中").toString(), 1);
			}
		} else {
			if (betDetail.contains("下")) {
				//下盘
				resultMap.put(winToBetTypeMap.get("下").toString(), 1);
			}
		}
		return generateWinResultMultBean(resultMap, context);
	}
}
