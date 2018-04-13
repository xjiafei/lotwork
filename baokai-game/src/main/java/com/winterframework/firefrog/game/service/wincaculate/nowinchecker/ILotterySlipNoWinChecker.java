package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;

/** 
* @ClassName ILotterySlipNoWinChecker 
* @Description 判断彩票注单是否中奖 前置验证 
* @author hugh  
* @date 2014年3月21日 下午4:05:49 
*  
*/
public interface ILotterySlipNoWinChecker {
	public boolean check(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception;
}
