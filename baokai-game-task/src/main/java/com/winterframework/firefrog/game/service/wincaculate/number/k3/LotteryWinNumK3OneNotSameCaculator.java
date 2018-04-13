package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNumK3OneNotSameCaculator 
* @Description 快3一不同号
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3OneNotSameCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int win = 0;
		String[] bets = betDetail.split(",");
		for (String string : bets) {
			if(resultCode.contains(string)){
				win++;
			}
		}
		return generateWinResultSingleBean(win);
	}

}
