package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.number.k3.LotteryWinNumK3TwoSameFXCaculator;
 
/**
 * 骰宝二同号复选
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月22日
 */
public class LotteryWinNumSbTwoSameFXCaculator extends LotteryWinNumK3TwoSameFXCaculator {
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception { 
		return super.getWinSlipNum(betDetail, resultCode, context);
	}
}
