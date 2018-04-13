package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115NumberDWDFSValidateExecutor 
* @Description:  n115任选一中一定位胆复式投注 注数验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115NumberDWDFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] bets = ((BetValidateContext) context).getBets();
		int totalBet = 0;
		//防止多余空格用文件分割方式处理
		for (String bet : bets) {
			if (!bet.contains("-")) {
				String temp[] = ValidateUtils.convertBet2String(bet.trim(), fileMode.file._value, " ");
				totalBet += temp.length;
			}
		}
		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
