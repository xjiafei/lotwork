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
 * 处理北京快乐8的如下彩种：上下盘
*/
public class LotteryWinNumInterestUpDownCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	private Map<String, Integer> winToBetTypeMap;

	public void setWinToBetTypeMap(Map<String, Integer> winToBetTypeMap) {
		this.winToBetTypeMap = winToBetTypeMap;
	}

	public Map<String, Integer> getWinToBetTypeMap() {
		return this.winToBetTypeMap;
	}

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		List<String> resultNumbers = new ArrayList<String>(Arrays.asList(resultCode.split(",")));
		int upNumber = 0;
		int downNumber = 0;
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
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
