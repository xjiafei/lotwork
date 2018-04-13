package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNumK3TwoNotSameDTCaculator 
* @Description 快3二不同号(胆拖选号) 
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3TwoNotSameDTCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int win = 0; 
		String[] tuos = betDetail.split("_T:")[1].split(",");
		String dan = betDetail.split("_T:")[0].substring(2);	
		if(resultCode.contains(dan)){
			for(String tuo :tuos){
				if(resultCode.contains(tuo)){
					win ++ ;
					if(win==2){	//最多中2注
						break;
					}
				}
			}
		} 
		return generateWinResultSingleBean(win);
	}

}
