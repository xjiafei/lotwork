package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberGroup3ValidateExecutor 
* @Description: 组3注数验证 
* @author Richard
* @date 2013-8-6 下午4:46:36 
*
 */
public class BetNumberGroup3ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ((BetValidateContext) context).getBets();

		int totbets = 2 * CombinUtil.combin(bets.length, 2);

		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());
		
		((BetValidateContext) context).setTotalBets(totbets);

	}

}
