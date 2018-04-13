package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberSimplexValidateExecutor 
* @Description: 组选6注数验证 
* @author Richard
* @date 2013-8-6 上午10:13:06 
*
 */
public class BetNumberZ6ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();

		int totalBets=CombinUtil.combin(bets.length, 3);

		((BetValidateContext) context).setTotalBets(totalBets);
	}
}
