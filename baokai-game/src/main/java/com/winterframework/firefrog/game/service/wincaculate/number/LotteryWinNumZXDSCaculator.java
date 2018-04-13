package com.winterframework.firefrog.game.service.wincaculate.number;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
 * 处理如下彩种
 * 五星直选单式  0,5
 * 四星直选单式  1,5
 * 前三直选单式  0, 3
 * 后三直选单式  2,5
 * 后二直选单式 3,5
 * 前二直选单式  0, 2
*/
public class LotteryWinNumZXDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		return generateWinResultSingleBean(betDetail.trim().contains(resultCode) == true ? 1 : 0);//文件模式中包含开奖号码那一注就算中奖
	}
}