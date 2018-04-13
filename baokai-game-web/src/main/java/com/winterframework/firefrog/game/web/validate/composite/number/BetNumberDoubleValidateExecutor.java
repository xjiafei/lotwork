package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: BetNumberDoubleValidateExecutor 
* @Description: 复式注数验证 
* @author Richard
* @date 2013-8-6 上午10:05:27 
*
 */
public class BetNumberDoubleValidateExecutor extends CompositeValidateExecutor<GameSlip> {

    //定位胆
    @PropertyConfig(value = "setCode.DWD")
    private int methodCode;
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();
		//验证投注数
		int totbets = 1;
		for (String bet : bets) {
			totbets *= bet.length();
		}
        //如果是一星
        if (validatedBean.getGameBetType().getGameSetCode() == methodCode) {
            totbets = 0;
            for (String bet : bets) {
                if (!bet.equals("-")) totbets += bet.length();
            }
        }
        ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}
}
