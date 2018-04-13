package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3BigLittleCaculator 
* @Description 快3大小  和值为4-10为小，和值为11-17为大  还要配置 验证三同号不算中奖
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3BigLittleCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {		
		int all =  CaculateUtil.getHezhi(resultCode);
		String result = all > 10 ? "大" : "小";
		int totalBet = 0;
		if(result.equals(betDetail)){
			totalBet = 1;
		}
		return generateWinResultSingleBean(totalBet);
	}

}
