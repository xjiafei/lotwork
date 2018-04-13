package com.winterframework.firefrog.game.service.wincaculate.number.k3;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName LotteryWinNumK3TwoSameFXCaculator 
* @Description 快3二同号复选
* @date 2015年3月4日 下午3:51:41 
*  
*/
public class LotteryWinNumK3TwoSameFXCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] sames = betDetail.split(",");	
		for (String same : sames) {			
			if(CaculateUtil.countStringTime(resultCode,same.substring(0, 1)) > 1){				
				return generateWinResultSingleBean(1);				
			}	
		}		
		return generateWinResultSingleBean(0);
	}

}
