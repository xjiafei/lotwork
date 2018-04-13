package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;


/**
 * 
* @ClassName: BetNumberSameValidateExecutor 
* @Description: 二星顛倒重複注數验证
* @author Ling
* @date 2016-12-09 上午18:01:00 
*
 */
public class BetNumberSameValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), " ",true);
		ValidateUtils.validateBetsSame(bets);
		((BetValidateContext) context).setBets(bets);
	}
}
