package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 *五星 组选120（杂牌）5,1
 *五星组选60（对子）4,2
 *五星组选30（两对）3,2
 *五星组选20（三条）3,3
 *五星组选10（葫芦）2,3
 *五星组选5（四条）2,4
 *四星组选24   4,1
 *四星组选12    3,2
 *四星组选6    2,2
 *四星组选4    2,3
*/
public class LotteryWinNum45ZXCaculator extends AbstractLotteryWinSlipNumCaculator {

	//	/** 
	//	* :统计开奖号码有几个不重复的数字
	//	*/
	//	private int countNum;
	//	/** 
	//	 * 获取开奖号码中重号的最大重号次数
	//	*/
	//	private int maxRepeatNum;

	/** 
	*  1:组选120,2，四星组选6，3四星组选24 0:其他 
	*/
	private int specileType;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String proResultCode = resultCode.substring(context.getResultCodeBeginIndex());
		String[] numbers = CaculateUtil.getResultNumbers(proResultCode);
		resultCode = resultCode.substring(context.getResultCodeBeginIndex());
		//		if (CaculateUtil.count(resultCode) == countNum && CaculateUtil.getMaxRepeatNumber(resultCode) == maxRepeatNum) {
		//			return proBetDetail.contains(proResultCode) ? 1 : 0;
		//		} else {
		//			return 0;
		//		}
		if (specileType == 1) {
			for (String number : numbers) {//看投注内容是否包含每一个开奖号码
				if (!betDetail.contains(number)) {
					return generateWinResultSingleBean(0);
				}
			}
			if(numbers.length <5){
				return generateWinResultSingleBean(0);
			}
			return generateWinResultSingleBean(1);
		} if (specileType == 3) {
			for (String number : numbers) {//看投注内容是否包含每一个开奖号码
				if (!betDetail.contains(number)) {
					return generateWinResultSingleBean(0);
				}
			}			
			return generateWinResultSingleBean(1);
		}else if (specileType == 2) {
			List<String> doubleNumber = CaculateUtil.getDoubleNumbers(resultCode);//获取开奖号码重号记录
			for (String number : doubleNumber) {//判断投注内容中是否包含所有重号
				if (!betDetail.contains(number)) {
					return generateWinResultSingleBean(0);
				}
			}
			return generateWinResultSingleBean(1);
		}else if (specileType == 4) {
			List<String> doubleNumber = CaculateUtil.getDoubleNumbers(resultCode);//获取开奖号码重号记录
			for (String number : doubleNumber) {//判断投注内容中是否包含所有重号
				if (!betDetail.contains(number)) {
					return generateWinResultSingleBean(0);
				}
			}
			String betDetail3[] = CaculateUtil.getResultNumbers(betDetail.split(",")[0]);
			String betDetail2[] = CaculateUtil.getResultNumbers(betDetail.split(",")[1]);
			
			boolean flag3 = false;
			for (String string : betDetail3) {
				int time3 = 0;
				for (String r : numbers) {
					if(string.equals(r)){
						time3 ++ ;
					}
				}
				if(time3 == 3){
					flag3 = true;
					break;
				}
			}
			
			boolean flag2 = false;
			for (String string : betDetail2) {
				int time2 = 0;
				for (String r : numbers) {
					if(string.equals(r)){
						time2 ++ ;
					}
				}
				if(time2 == 2){
					flag2 = true;
					break;
				}
			}
			
			if(flag3 && flag2){
				return generateWinResultSingleBean(1);
			}else{
				return generateWinResultSingleBean(0);
			}
			
			
		} else {
			return generateWinResultSingleBean(charge(proResultCode, betDetail));
		}
	}

	/** 
	* @Title: chargeWX 
	* @Description: 五星组选60，30,20,10,5 四星组选12，6，4中奖判断公用代码
	* @param resultCode
	* @param betDetail
	* @return
	*/
	private Integer charge(String resultCode, String betDetail) {
		String[] betNumbers;
		List<String> doubleNumber;
		List<String> sigleNumber;
		betNumbers = betDetail.split(",");
		doubleNumber = CaculateUtil.getDoubleNumbers(resultCode);//获取开奖号码重号记录
		sigleNumber = CaculateUtil.getSigleNumbers(resultCode);//获取开奖号码单号记录
		for (String number : doubleNumber) {//判断投注内容中是否包含所有重号
			if (!betNumbers[0].contains(number)) {
				return 0;
			}
		}
		for (String number : sigleNumber) {//判断投注内容中是否包含所有单号
			if (!betNumbers[1].contains(number)) {
				return 0;
			}
		}
		return 1;
	}

	public void setSpecileType(int specileType) {
		this.specileType = specileType;
	}
}
