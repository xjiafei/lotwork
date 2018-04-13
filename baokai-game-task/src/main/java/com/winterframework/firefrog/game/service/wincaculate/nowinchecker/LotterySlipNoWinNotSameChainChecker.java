package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName: LotteryWinNumMustZLChainCaculator 
* @Description: 统计不重复的数，为notSameNum就传递，其他就不传递 
* （投注计算值  规则  配置大小notSameNum）
* @author  hugh
* @date 2014年3月18日 下午2:29:59 
*  
*/
public class LotterySlipNoWinNotSameChainChecker extends AbstractLotterySlipNoWinChainChecker {

	private int notSameNum;

	/** 
	 * == eq  
	 * != ne 
	 * <  lt 
	 * >  gt  
	 * <= le  
	 * >= ge
	* @Fields compareRule : 配置 eq ne 字符串
	*/
	private String compareRule;

	@Override
	public boolean doCheck(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {

		String tempResultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(),
				context.getResultCodeEndIndex());

		//统计开奖号码有几个不重复的数字
		return CaculateUtil.compareNum(compareRule, CaculateUtil.count(tempResultCode), notSameNum);

	}

	public String getCompareRule() {
		return compareRule;
	}

	public void setCompareRule(String compareRule) {
		this.compareRule = compareRule;
	}

	public int getNotSameNum() {
		return notSameNum;
	}

	public void setNotSameNum(int notSameNum) {
		this.notSameNum = notSameNum;
	}

}
