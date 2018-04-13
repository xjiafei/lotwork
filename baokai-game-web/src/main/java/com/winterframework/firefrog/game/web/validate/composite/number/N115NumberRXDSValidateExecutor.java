package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115NumberRXFSValidateExecutor 
* @Description:  n115任选单式式投注 注数验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115NumberRXDSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
	    String[] bets = ((BetValidateContext) context).getBets();
		
		ValidateUtils.validateBetsCount(bets.length, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(bets.length);
	}
}
