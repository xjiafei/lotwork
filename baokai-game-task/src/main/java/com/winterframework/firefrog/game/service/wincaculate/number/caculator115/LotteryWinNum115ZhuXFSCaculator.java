package com.winterframework.firefrog.game.service.wincaculate.number.caculator115;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;

/** 
* @ClassName LotteryWinNum115ZhuXFSCaculator 
* @Description 11选5 组选复式
* @author  hugh
* @date 2014年5月16日 上午11:23:27 
*  
*/
public class LotteryWinNum115ZhuXFSCaculator extends AbstractLotteryWinSlipNumCaculator {

	private LotteryWinNum115CommonCaculator common = new LotteryWinNum115CommonCaculator();
	
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {
		List<String> simpleBetDetail = Arrays.asList(betDetail.split(","));		
		return common.getZhuXWinSlipNum(simpleBetDetail, resultCode, context);
	}
}