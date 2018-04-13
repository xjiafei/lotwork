package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatDDSValidateExecutor 
* @Description:  n115订单双投注 格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatDDSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String str="5单0双|4单1双|3单2双|2单3双|1单4双|0单5双";
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "\\|");
		ValidateUtils.checkRepeat(bets);
		for (String bet : bets) {
			if (!str.contains(bet)&&bet.length()!=4) {
				throw new GameBetContentPatternErrorException();
			}

		}
		((BetValidateContext) context).setBets(bets);
	}
}
