package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115DDSCaculator 
* @Description 11选5 定单双
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115DDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	private Map<String, String> helpMap;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int jiNum = 0;//奇数
		int ouNum = 0;//偶数

		String[] simpleResultCode = resultCode.split(",");

		for (String string : simpleResultCode) {
			int num = Integer.parseInt(string);
			if (num % 2 == 0) {
				ouNum++;
			} else {
				jiNum++;
			}
		}
		String result = jiNum + "单" + ouNum + "双";

		List<String> simpleBetDetail = Arrays.asList(betDetail.split("\\|"));

		if (simpleBetDetail.contains(result)) {
			Map<String, Integer> multiWin = new HashMap<String, Integer>();
			multiWin.put(helpMap.get(jiNum + ""), 1);
			return generateWinResultMultBean(multiWin, context);
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