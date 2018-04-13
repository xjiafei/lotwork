package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.number.k3.LotteryWinNumK3ThreeSameAllCaculator;
 
/**
 * 骰宝三同号通选 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月22日
 */
public class LotteryWinNumSbThreeSameAllCaculator extends LotteryWinNumK3ThreeSameAllCaculator {
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception {
		// TODO Auto-generated method stub
		return super.getWinSlipNum(betDetail, resultCode, context);
	}

}
