package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 *五星一码不定位  	0		1
 *五星二码不定位	1		2
 *五星三码不定位  	2		3
 *四星一码不定位 	0       1
 *四星二码不定位	1       2
 *后三一码不定位 	0       1
 *后三二码不定位 	1       2
 *前三一码不定位	0       1
 *前三二码不定位	1       2
 *趣味中的一帆风顺	0       1
*/
public class LotteryWinNumBDWCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* :统计开奖号码有几个不重复的数字一码不定位为0，二码不定位为1，三码不定位为2）
	*/
	//	private int countNum;
	/** 
	* 检查第一位（一码不定位） 1：一码不定位，2：二码不定位。3：三码不定位
	*/
	private int checkFirst;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] betNumbers;//存储投注内容分割后的值
		int totalBet = 0;//中奖注数
		resultCode = context.getResultCode(resultCode);
		//		if (CaculateUtil.count(resultCode) <= countNum) {// 如果本期开奖为豹子号以及组选10和组选5号码, 全部未中奖
		//			return 0;
		//		}
		//获取投注号码所有的组合
		List<String> combinationW3s = CaculateUtil.getCombinations(Arrays.asList(betDetail.split(",")), checkFirst);
		for (String combination : combinationW3s) {
			//一码不定位
			betNumbers = combination.split(",");
			if (checkFirst == 1) {
				if (resultCode.contains(betNumbers[0])) {
					totalBet++;
				}
			}
			//二码不定位
			if (checkFirst == 2) {
				//判断第三位是否匹配
				if (resultCode.contains(betNumbers[0]) && resultCode.contains(betNumbers[1])) {
					totalBet++;
				}
			}
			//三码不定位
			if (checkFirst == 3) {
				//判断第三位是否匹配
				if (resultCode.contains(betNumbers[0]) && resultCode.contains(betNumbers[1])
						&& resultCode.contains(betNumbers[2])) {
					totalBet++;
				}
			}
		}
		return generateWinResultSingleBean(totalBet);
	}

	public int getCheckFirst() {
		return checkFirst;
	}

	public void setCheckFirst(int checkFirst) {
		this.checkFirst = checkFirst;
	}

}
