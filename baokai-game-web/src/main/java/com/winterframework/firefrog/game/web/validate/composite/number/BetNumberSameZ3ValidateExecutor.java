package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;


/**
 * 
* @ClassName: BetNumberSameZ3ValidateExecutor 
* @Description: 三星顛倒重複注數验证
* @author Andy
* @date 2016-01-09
*
 */
public class BetNumberSameZ3ValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//只針順利秒秒,重慶,新疆,黑龍江,天津時時彩,超級2000秒秒(APP版),已及上海時時彩
		Long[] LotteryIds = {99112L,99101L,99103L,99105L,99104L,99113L,99107L};
		for(int i = 0; i<LotteryIds.length; i++){
			
			if(validatedBean.gameOrder.getLottery().getLotteryId().equals(LotteryIds[i])){
				String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), " ",true);
				ValidateUtils.validateBetsSameZ3(bets);
				((BetValidateContext) context).setBets(bets);
			}
		}
	}

}
