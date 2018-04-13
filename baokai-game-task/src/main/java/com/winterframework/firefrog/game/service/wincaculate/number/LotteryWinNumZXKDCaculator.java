package com.winterframework.firefrog.game.service.wincaculate.number;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 * 后三/前三/前二/后二直选跨度
*/
public class LotteryWinNumZXKDCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//跨度变量
		int kuadu = CaculateUtil.getKuadu(resultCode);
		//存储投注内容分割后的值
		String[] betNumbers = betDetail.split(",");
		return generateWinResultSingleBean(CaculateUtil.isExist(betNumbers, kuadu) == true ? 1 : 0);
	}
}
