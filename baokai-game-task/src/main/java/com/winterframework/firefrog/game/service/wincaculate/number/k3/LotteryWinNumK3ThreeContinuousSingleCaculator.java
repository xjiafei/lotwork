package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

 
/**
 *LotteryWinNumK3ThreeContinuousSingleCaculator
 * @ClassName 快3三连号单选 
 * @Description
 * @author ibm
 * 2017年7月3日
 */
public class LotteryWinNumK3ThreeContinuousSingleCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int kuadu = CaculateUtil.getKuadu(resultCode);
		int maxRepeatNumber = CaculateUtil.getMaxRepeatNumber(resultCode);	
		int win = 0;
		if(kuadu==2 && maxRepeatNumber==1){
			if(CaculateUtil.getHezhi(resultCode)==CaculateUtil.getHezhi(betDetail)){
				win = 1;
			}
		}
		return generateWinResultSingleBean(win);
	}

}
