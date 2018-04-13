package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.BetCalUtil;

/**
 * 
* @ClassName: BetNumberYXValidateExecutor 
* @Description:一星验证
* @author Richard
* @date 2013-12-27 下午5:32:15 
*
 */
public class BetNumberYXValidateExecutor extends CompositeValidateExecutor<GameSlip> {

		@Override
		public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

			String[] bets = ((BetValidateContext) context).getBets();
			int totBet = 0;
			for(String bet : bets){
				int n = BetCalUtil.getN(bet);
				totBet += n;
			}
			
			ValidateUtils.validateBetsCount(totBet, validatedBean.getTotalBet());

			((BetValidateContext) context).setTotalBets(totBet);
		}
}
