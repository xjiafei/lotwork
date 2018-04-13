package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatBDValidateExecutor 
* @Description: 组选包胆
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatBDValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), ",");
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.valideateBetformat(bets.length, 1);
		for(String bet:bets){
			ValidateUtils.validateBetContentLength(bet, 1);
		}
		((BetValidateContext) context).setBets(bets);
	}
}
