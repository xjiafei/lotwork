package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * 
* @ClassName: BetNumberSimplexValidateExecutor 
* @Description: 单式注数验证 
* @author Richard
* @date 2013-8-6 上午10:13:06 
*
 */
public class BetNumberSimplexValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();

		ValidateUtils.validateBetsCount(bets.length, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(bets.length);
	}
}
