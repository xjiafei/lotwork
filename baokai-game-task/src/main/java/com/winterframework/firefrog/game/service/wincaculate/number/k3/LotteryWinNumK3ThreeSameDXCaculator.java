package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNumK3ThreeSameDXCaculator 
* @Description 快3三同号单选
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3ThreeSameDXCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int win = 0;
		if(betDetail.contains(resultCode)){
			win = 1;
		}
		return generateWinResultSingleBean(win);
	}

}
