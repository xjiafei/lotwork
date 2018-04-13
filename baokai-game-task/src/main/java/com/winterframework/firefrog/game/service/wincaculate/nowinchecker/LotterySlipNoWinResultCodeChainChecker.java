package com.winterframework.firefrog.game.service.wincaculate.nowinchecker;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
* @ClassName: LotteryWinNumMustZLChainCaculator 
* @Description: 比较resultCode长度，传递或不传递 
* （投注计算值  规则  配置大小）
* @author  hugh
* @date 2014年3月18日 下午2:29:59 
*  
*/
public class LotterySlipNoWinResultCodeChainChecker extends AbstractLotterySlipNoWinChainChecker {

	private int resultCodeLength;
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

		return CaculateUtil.compareNum(compareRule, resultCode.length(), resultCodeLength);

	}

	public int getResultCodeLength() {
		return resultCodeLength;
	}

	public void setResultCodeLength(int resultCodeLength) {
		this.resultCodeLength = resultCodeLength;
	}

	public String getCompareRule() {
		return compareRule;
	}

	public void setCompareRule(String compareRule) {
		this.compareRule = compareRule;
	}

}
