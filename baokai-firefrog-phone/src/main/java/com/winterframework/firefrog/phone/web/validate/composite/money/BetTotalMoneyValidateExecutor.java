package com.winterframework.firefrog.phone.web.validate.composite.money;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/**
 * 
* @ClassName: BetTotalMoneyValidateExecutor 
* @Description: 投注总金额验证 
* @author Richard
* @date 2013-8-6 下午2:27:21 
*
 */
public class BetTotalMoneyValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		// 验证投注金额
		ValidateUtils.validateTotalMoney(validatedBean.getTotalAmount(), ((BetValidateContext) context).getTotalBets(),
				validatedBean.getMultiple(), validatedBean.getMoneyMode().getValue());
	}
}
