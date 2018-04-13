package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

public class LHCFormatBanboValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),
                validatedBean.getGameOrder().getFileMode().getValue(), "\\|");
        String str = "红大、红小、红单、红双、" +
	        		 "蓝大、蓝小、蓝单、蓝双、" +
	        		 "绿大、绿小、绿单、绿双" ;
        ValidateUtils.checkRepeat(bets);
        for(String bet : bets) {
            if (str.indexOf(bet)<=-1) {
                throw new GameBetContentPatternErrorException();
            }
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
