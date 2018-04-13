package com.winterframework.firefrog.game.service.wincaculate.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultMultBean;
import com.winterframework.firefrog.common.wincaculate.WinResultSingleBean;

public abstract class AbstractLotteryWinSlipNumCaculator implements ILotteryWinSlipNumCaculator {

	public abstract IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception;

	/** 
	* @Title: generateWinResultSingleBean 
	* @Description: 只有一种中奖结果的生成方法
	* @param winNumber
	* @return
	*/
	public IWinResultBean generateWinResultSingleBean(Integer winNumber) {
		return new WinResultSingleBean(winNumber);
	}

	/** 
	* @Title: generateWinResultMultBean 
	* @Description: 同时中多种将的情况
	* @param assistCodeWinNumberMap
	* @param context
	* @return
	*/
	public IWinResultBean generateWinResultMultBean(Map<String, Integer> assistCodeWinNumberMap,
			ILotterySlipNumCaculatorContext context) {
		LotteryPlayMethodKeyGenerator keyGenerator = (LotteryPlayMethodKeyGenerator) context.getKeyGenerator();
		String betTypeCode = keyGenerator.getBetTypeCode() + "_";
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if (assistCodeWinNumberMap != null) {
			for (Entry<String, Integer> temp : assistCodeWinNumberMap.entrySet()) {
				resultMap.put(betTypeCode + temp.getKey(), temp.getValue());
			}
		}
		return new WinResultMultBean(resultMap);
	}
}
