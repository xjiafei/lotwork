package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatZ3ValidateExecutor 
* @Description: 组3投注格式验证
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatZ6DSValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),  validatedBean.getGameOrder().getFileMode().getValue(), " ");
		ValidateUtils.checkIsNumber(bets);
		for (String s : bets) {
			ValidateUtils.valideateBetformat(s.length(), 3);
			ValidateUtils.checkZ6content(s);
		}

		ValidateUtils.checkRepeat(bets);
		((BetValidateContext) context).setBets(bets);
	}
}
