package com.winterframework.firefrog.game.service.wincaculate.number;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 *定位胆
*/
public class LotteryWinNumDWDCaculator extends AbstractLotteryWinSlipNumCaculator {
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] betNumbers = betDetail.split(",");//分割投注号
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);//获取开奖号码分割后的字符串数组
		int totalBet = 0;//中奖注数
		if (betNumbers.length != numbers.length) {//一星定位胆和开奖号码一样，分割后都是5位数组，假如格式不对，则计奖失败
			return generateWinResultSingleBean(0);
		}
		for (int i = 0; i < numbers.length; i++) {//每一位分别判断是否投注内容有包含，包含则中奖注数加1
			if (betNumbers[i].contains(numbers[i])) {
				totalBet++;
			}
		}
		return generateWinResultSingleBean(totalBet);
	}
}
