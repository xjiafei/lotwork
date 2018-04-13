package com.winterframework.firefrog.game.service.wincaculate.number;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 * 后二组选复式3,5,1,1
 * 前二组选复式0,2,1,2
 * 后二组选单式3,5,1,3
 * 前二组选单式0,2,1,3
*/
public class LotteryWinNumZSLCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 彩种类型
	*/
	private int lotteryType;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);//获取开奖号码分割后的字符串数组
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		int length = numbers.length;
		int num = 0;
		String[] betDetailList = betDetail.split(" ");//单式多注用空格分隔
		switch (lotteryType) {		
		case 1://后二组选复式/单式			
			for (int i = 0; i < betDetailList.length; i++) {
				if (betDetailList[i].contains(numbers[length-2]) && betDetailList[i].contains(numbers[length-1])){
					num ++;
				}
			}
			break;
		case 2://前二组选复式/单式
			for (int i = 0; i < betDetailList.length; i++) {
				if (betDetailList[i].contains(numbers[0]) && betDetailList[i].contains(numbers[1])) {
					num ++;
				}
			}
			break;
		default:			
		}
		return generateWinResultSingleBean(num);
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}

}
