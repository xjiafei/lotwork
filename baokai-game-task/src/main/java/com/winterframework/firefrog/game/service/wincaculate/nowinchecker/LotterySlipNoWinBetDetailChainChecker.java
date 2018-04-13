package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName: LotteryWinNumMustZLChainCaculator 
* @Description: 一星定位胆和开奖号码一样，分割后都是5位数组，假如格式不对，则计奖失败
* @author  hugh
* @date 2014年3月18日 下午2:29:59 
*  
*/
public class LotterySlipNoWinBetDetailChainChecker extends AbstractLotterySlipNoWinChainChecker {

	@Override
	public boolean doCheck(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {

		String[] betNumbers = betDetail.split(",");//分割投注号
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);//获取开奖号码分割后的字符串数组
		if (betNumbers.length == numbers.length) {//一星定位胆和开奖号码一样，分割后都是5位数组，假如格式不对，则计奖失败
			return true;
		} else {
			return false;
		}

	}

}
