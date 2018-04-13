package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3HZCaculator 
* @Description 快3和值 
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3HZCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int all =  CaculateUtil.getHezhi(resultCode);
		
		if((","+betDetail+",").contains(","+all+",")){
			Map<String, Integer> winResultMap = new HashMap<String, Integer>();
			winResultMap.put(getWinK3TypeMap().get(all), 1);
			return generateWinResultMultBean(winResultMap, context);
		}		
		return generateWinResultMultBean(null, context);
	}

	public Map<Integer, String> getWinK3TypeMap() {
		return winK3TypeMap;
	}

	public void setWinK3TypeMap(Map<Integer, String> winK3TypeMap) {
		this.winK3TypeMap = winK3TypeMap;
	}

	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	private Map<Integer, String> winK3TypeMap;
	
	
	
	
}
