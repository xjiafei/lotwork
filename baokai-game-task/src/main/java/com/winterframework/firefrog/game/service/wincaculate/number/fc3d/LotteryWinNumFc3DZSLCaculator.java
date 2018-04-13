package com.winterframework.firefrog.game.service.wincaculate.number.fc3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class LotteryWinNumFc3DZSLCaculator extends AbstractLotteryWinSlipNumCaculator {
	
	private static final Logger log = LoggerFactory.getLogger(LotteryWinNumFc3DZSLCaculator.class);

	/** 
	* 彩种类型
	*/
	private int lotteryType;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		log.info("投注内容：betDetail =" + betDetail + "，开奖号码="+ resultCode);
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);//获取开奖号码分割后的字符串数组
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//resultCode = resultCode.substring(context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//		if (CaculateUtil.count(resultCode) == countNum) {// 本期开奖为豹子号, 则本期全部组选复式未中奖.
		//			return 0;
		//		}
		switch (lotteryType) {
		case 1://后二组选复式/单式
			if (betDetail.contains(numbers[1]) && betDetail.contains(numbers[2])) {
				return generateWinResultSingleBean(1);
			} else {
				return generateWinResultSingleBean(0);
			}
		case 2://前二组选复式/单式
			if (betDetail.contains(numbers[0]) && betDetail.contains(numbers[1])) {
				return generateWinResultSingleBean(1);
			} else {
				return generateWinResultSingleBean(0);
			}
		default:
			return generateWinResultSingleBean(0);
		}
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}

}
