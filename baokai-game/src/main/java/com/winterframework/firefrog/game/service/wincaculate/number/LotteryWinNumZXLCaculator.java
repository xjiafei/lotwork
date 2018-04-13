package com.winterframework.firefrog.game.service.wincaculate.number;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 * 前三组选组三 0,3,2
 * 前三组选组六0,3,3
 * 后三组选组三2,5,2
 * 后三组选组六2,5,3
*/
public class LotteryWinNumZXLCaculator extends AbstractLotteryWinSlipNumCaculator {

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//获取开奖号码分割后的字符串数组
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);
		for (String number : numbers) {
			if (!betDetail.contains(number)) {
				return generateWinResultSingleBean(0);//假如投注内容不包含开奖数据，则不中奖
			}
		}
		return generateWinResultSingleBean(1);//其他情况算中奖
	}
}
