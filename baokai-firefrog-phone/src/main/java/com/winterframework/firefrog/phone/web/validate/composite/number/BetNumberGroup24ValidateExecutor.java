package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberGroup24ValidateExecutor 
* @Description: 组选24注数验证
* @author Richard
* @date 2013-8-6 下午2:00:11 
*
 */
public class BetNumberGroup24ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ((BetValidateContext) context).getBets();

		//C(n,4)   n>=4
		int totbets = CombinUtil.combin(bets.length, 4);

		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}
}
