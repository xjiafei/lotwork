package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.number.k3.LotteryWinNumK3OneNotSameCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;
 
/**
 * 骰宝一不同号
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月22日
 */
public class LotteryWinNumSbOneNotSameCaculator extends LotteryWinNumK3OneNotSameCaculator {
	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	private Map<Integer, String> winAssistTypeMap;
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception {
		/**
		 * 1.是否中奖
		 * 2.是单、双、全
		 */
		if(resultCode.contains(betDetail)){
			Map<String, Integer> winResultMap = new HashMap<String, Integer>();
			winResultMap.put(getWinAssistTypeMap().get(CaculateUtil.getNumberCount(resultCode,Integer.valueOf(betDetail))), 1);
			return generateWinResultMultBean(winResultMap, context);
		}
		return generateWinResultMultBean(null, context);
	}

	public Map<Integer, String> getWinAssistTypeMap() {
		return winAssistTypeMap;
	}

	public void setWinAssistTypeMap(Map<Integer, String> winAssistTypeMap) {
		this.winAssistTypeMap = winAssistTypeMap;
	}

}
