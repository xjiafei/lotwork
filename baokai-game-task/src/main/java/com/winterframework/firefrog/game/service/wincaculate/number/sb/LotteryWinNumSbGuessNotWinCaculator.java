package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;

/**
 * 骰寶猜不中玩法中獎計算
 * @author Pogi
 */
public class LotteryWinNumSbGuessNotWinCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		if(context == null) {
			throw new NullPointerException("投注本文不得為空值");
		}
		Long lotteryId = ((LotteryPlayMethodKeyGenerator)context.getKeyGenerator()).getLotteryType();
		if(StringUtils.isBlank(betDetail)){
			throw new NullPointerException("(" + lotteryId + ")投注內容錯誤:" + betDetail);
		}
		if(StringUtils.isBlank(resultCode)){
			throw new NullPointerException("(" + lotteryId + ")開獎內容錯誤:" + resultCode);
		}
		
		if(resultCode.contains(betDetail)) {
			return generateWinResultSingleBean(0);
		} else {
			return generateWinResultSingleBean(1);
		}
	}
}
