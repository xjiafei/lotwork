package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115CZWCaculator 
* @Description 11选5 猜中位
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115CZWCaculator extends AbstractLotteryWinSlipNumCaculator {

	private Map<String, String> helpMap;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		//获取中位
		String[] simpleResultCode = resultCode.split(",");
		Arrays.sort(simpleResultCode);

		//获取投注数
		List<String> simpleBetDetail = Arrays.asList(betDetail.split(","));
		if (simpleBetDetail.contains(simpleResultCode[2])) {
			Map<String, Integer> mulWin = new HashMap<String, Integer>();
			mulWin.put(helpMap.get(simpleResultCode[2]), 1);
			return generateWinResultMultBean(mulWin, context);
		} else {
			return generateWinResultMultBean(null, context);
		}

	}

	public Map<String, String> getHelpMap() {
		return helpMap;
	}

	public void setHelpMap(Map<String, String> helpMap) {
		this.helpMap = helpMap;
	}

}