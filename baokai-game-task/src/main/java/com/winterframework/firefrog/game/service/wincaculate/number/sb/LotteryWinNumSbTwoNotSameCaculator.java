package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.number.k3.LotteryWinNumK3TwoNotSameCaculator;
 
/**
 * 骰宝二不同号(标准选号) 
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月22日
 */
public class LotteryWinNumSbTwoNotSameCaculator extends LotteryWinNumK3TwoNotSameCaculator {
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception {
		// TODO Auto-generated method stub
		return super.getWinSlipNum(betDetail, resultCode, context);
	}
}
