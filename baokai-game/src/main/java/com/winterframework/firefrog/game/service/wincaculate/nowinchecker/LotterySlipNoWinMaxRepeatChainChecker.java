package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName: LotteryWinNumMustZLChainCaculator 
* @Description: 统计开奖号码中重号的最大重号次数(去重后的数) ，比较maxRepeatNum，传递或不传递 
* （投注计算值  规则  配置大小maxRepeatNum）
* @author  hugh
* @date 2014年3月18日 下午2:29:59 
*  
*/
public class LotterySlipNoWinMaxRepeatChainChecker extends AbstractLotterySlipNoWinChainChecker {

	private int maxRepeatNum;
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

	public int getMaxRepeatNum() {
		return maxRepeatNum;
	}

	public void setMaxRepeatNum(int maxRepeatNum) {
		this.maxRepeatNum = maxRepeatNum;
	}

	@Override
	public boolean doCheck(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context) throws Exception {

		String tempResultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(),
				context.getResultCodeEndIndex());

		//获取开奖号码中重号的最大重号次数 
		return CaculateUtil.compareNum(compareRule, CaculateUtil.getMaxRepeatNumber(tempResultCode), maxRepeatNum);

	}

	public String getCompareRule() {
		return compareRule;
	}

	public void setCompareRule(String compareRule) {
		this.compareRule = compareRule;
	}

}
