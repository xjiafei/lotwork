package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNumK3TwoNotSameCaculator 
* @Description 快3二不同号(标准选号) 
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3TwoNotSameCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
			int win = 0;
			int count = 0;
			String[] bets = betDetail.split(",");
			for(String bet :bets){
				if(resultCode.contains(bet)){
					count ++ ;
				}
			}
			if(count == 2){
				win = 1;
			}else if(count == 3){
				win = 3;
			}
			return generateWinResultSingleBean(win);
	}

}
