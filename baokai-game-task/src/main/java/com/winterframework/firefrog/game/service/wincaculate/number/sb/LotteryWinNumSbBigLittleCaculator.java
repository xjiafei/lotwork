package com.winterframework.firefrog.game.service.wincaculate.number.sb;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.number.k3.LotteryWinNumK3BigLittleCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;
 
/**
 * 骰宝大小  和值为4-10为小，和值为11-17为大  还要配置 验证三同号不算中奖
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月22日
 */
public class LotteryWinNumSbBigLittleCaculator extends LotteryWinNumK3BigLittleCaculator {
	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode,
			ILotterySlipNumCaculatorContext context) throws Exception {
		return super.getWinSlipNum(betDetail, resultCode, context);
	}

}
