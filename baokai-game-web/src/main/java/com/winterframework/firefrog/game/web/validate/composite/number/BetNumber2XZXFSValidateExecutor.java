package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumber2XZXFSValidateExecutor 
* @Description: 二星组选复式格式验证，前二，后二
* @author Richard
* @date 2013-8-6 上午10:13:06 
*
 */
public class BetNumber2XZXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();
		
		int totalBet=CombinUtil.combin(bets.length, 2);

		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
