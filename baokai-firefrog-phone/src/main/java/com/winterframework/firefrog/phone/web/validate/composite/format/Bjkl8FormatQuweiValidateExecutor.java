package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;

/**
 *
 * Created by lex on 2014/3/31.
 */
public class Bjkl8FormatQuweiValidateExecutor extends CompositeValidateExecutor<GameSlip> {
    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

        String[] bets = new String[1];
        bets[0] = validatedBean.getBetDetail();
        String str = "上、中、下、奇、和、偶、大单、大双、小单小双";
        if (str.indexOf(bets[0])<=-1) {
            throw new GameBetContentPatternErrorException();
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
