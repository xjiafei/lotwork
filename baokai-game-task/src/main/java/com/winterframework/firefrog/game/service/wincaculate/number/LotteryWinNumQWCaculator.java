package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
 * 处理如下彩种（一帆风顺在一码不定位中处理）
 *好事成双 2
 *三星报喜 3
 *四季发财 4
*/
public class LotteryWinNumQWCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 出现的最大次数 好事成双2,三星报喜3，四季发财
	*/
	private int type;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);
		List<String> doubleNumber = new ArrayList<String>();//开奖号码中重号保存数组
		int totalBet = 0;//中奖注数
		//		if (CaculateUtil.getMaxRepeatNumber(resultCode) < maxRepeatNumber) {// 如果本期开奖为组选120号码, 全部未中奖
		//			return 0;
		//		}
		doubleNumber = CaculateUtil.getDoubleNumbers(resultCode);
		for (String number : doubleNumber) {
			
			if (betDetail.contains(number)) {
				if(type == 3){
					int time = 0;
					for (String string : numbers) {
						if(number.equals(string)){
							time ++ ;
						}
					}
					if (time>2) {
						totalBet++;
					}				
				}else{
					totalBet++;
				}
			}
		}
		
	
		return generateWinResultSingleBean(totalBet);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}