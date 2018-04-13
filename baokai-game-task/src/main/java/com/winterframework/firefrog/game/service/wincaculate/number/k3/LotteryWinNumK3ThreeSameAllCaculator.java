package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3ThreeSameAllCaculator 
* @Description 快3三同号通选 
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3ThreeSameAllCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int maxRepeatNumber = CaculateUtil.getMaxRepeatNumber(resultCode);	
		int win = 0;
		if(maxRepeatNumber==3){
			win = 1;
		}
		return generateWinResultSingleBean(win);
	}

}
