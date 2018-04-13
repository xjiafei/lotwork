package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3ThreeNotSameCaculator 
* @Description 快3三不同号(标准选号)
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3ThreeNotSameCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int win = 0; 
		String[] bets = betDetail.split(",");
		List<String> betNumbers = Arrays.asList(bets);
		List<String> zuhe =  CaculateUtil.getCombinations(betNumbers, 3);	//组合
		for (String string : zuhe) {
			List<String> pailie=CaculateUtil.pailie(string.replace(",", ""));	//排列
			for(String bet:pailie){				
				if(bet.equals(resultCode)){
					win++;
					break;
				}
			}
		}	
		return generateWinResultSingleBean(win);
	}

}
