package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberGroup120ValidateExecutor 
* @Description: 组选120 数组验证
* @author Richard
* @date 2013-8-5 下午3:55:53 
*
 */
public class BetNumberGroup120ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();

		int length = bets.length;

		//验证投注位数是至少5位
		ValidateUtils.valideateBetformatAtleast(length, 5);

		//验证投注数 C(n,5)   n>=5
		int totbets = CombinUtil.combin(length, 5);
		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}
}
