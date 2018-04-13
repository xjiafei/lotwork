package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115ZhiXDSCaculator 
* @Description 11选5 直选单式
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115ZhiXDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		//int winNum = 0;

		String[] simpleResultCode = resultCode.split(",");
		String[] details = betDetail.split(",");
		for (String detail : details) {
			String[] simpleBetDetail = detail.split(" ");
			for (int i = 0; i < simpleBetDetail.length; i++) {
				if (!simpleBetDetail[i].equals(simpleResultCode[i])) {
					return generateWinResultSingleBean(0);
				}
			}
		}

		return generateWinResultSingleBean(1);
	}
}