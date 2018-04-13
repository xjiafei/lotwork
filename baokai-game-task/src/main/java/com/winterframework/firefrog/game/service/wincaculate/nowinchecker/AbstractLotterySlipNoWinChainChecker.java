/**   
* @Title: LotteryWinSlipNumChainCaculator.java 
* @Package com.winterframework.firefrog.game.wincaculate.business 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 下午1:24:32 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;

/** 
* @ClassName: LotteryWinSlipNumChainCaculator 
* @Description: 计算奖金数抽象父类  （责任链模式，子类实现caculate抽象方法） 
* @author hugh 
* @date 2014-3-18 下午1:24:32 
*  
*/
public abstract class AbstractLotterySlipNoWinChainChecker implements ILotterySlipNoWinChecker {

	private AbstractLotterySlipNoWinChainChecker nextChecker;

	/**
	* Title: getWinSlipNum
	* Description:
	* @param betDetail
	* @param resultCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator#getWinSlipNum(java.lang.String, java.lang.String) 
	*/
	@Override
	public boolean check(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {
		boolean result = doCheck(betDetail, resultCode, context);
		//有下个处理类，且自己本身结果不是不继续
		if (result && nextChecker != null) {

			result = nextChecker.check(betDetail, resultCode, context);
		}
		return result;
	}

	protected abstract boolean doCheck(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception;

	public AbstractLotterySlipNoWinChainChecker getNextChecker() {
		return nextChecker;
	}

	public void setNextChecker(AbstractLotterySlipNoWinChainChecker nextChecker) {
		this.nextChecker = nextChecker;
	}

}
