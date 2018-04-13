package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3TwoSameDXCaculator 
* @Description 快3二同号单选
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3TwoSameDXCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] sames = betDetail.split("#")[0].split(" ");
		String[] others =  betDetail.split("#")[1].split(" ");	
		
		for (String same : sames) {			
			if(CaculateUtil.countStringTime(resultCode,same.substring(1)) > 1){
				for (String other : others) {
					if(resultCode.contains(other)){
						return generateWinResultSingleBean(1);
					}
				}	
			}	
		}		
		return generateWinResultSingleBean(0);
	}
	
}
