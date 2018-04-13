package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3SingleDoubleCaculator 
* @Description 快3单双  还要配置 验证三同号不算中奖
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3SingleDoubleCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int all =  CaculateUtil.getHezhi(resultCode);
		String result = all % 2 == 0 ? "双" : "单";
		int totalBet = 0;
		if(result.equals(betDetail)){
			totalBet = 1;
		}
		return generateWinResultSingleBean(totalBet);
	}

}
