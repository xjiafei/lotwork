package com.winterframework.firefrog.phone.web.validate.business;

import org.springframework.util.Assert;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;

public class BaseCompositeValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String betDetail = validatedBean.getBetDetail();
		Assert.notNull(betDetail, "投注内容不能为空");
	
		super.execute(validatedBean, context);
	}
}
