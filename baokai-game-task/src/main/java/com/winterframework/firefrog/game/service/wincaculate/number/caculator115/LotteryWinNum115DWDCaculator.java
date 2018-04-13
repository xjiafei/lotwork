package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115DWDCaculator 
* @Description 11选5 定位胆
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115DWDCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) {
		int winNum = 0;

		String[] simpleResultCode = resultCode.split(",");
		String[] simpleBetDetail = betDetail.split(",");

		outer : for (int i = 0; i < simpleBetDetail.length; i++) {
			String[] trueSimpleBetDetail = simpleBetDetail[i].split(" ");
			for (String string : trueSimpleBetDetail) {
				if (string.equals(simpleResultCode[i])) {
					winNum++;
					continue outer;
				}
			}
			
		}

		return generateWinResultSingleBean(winNum);
	}

}