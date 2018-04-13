package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

import java.util.Map;

/**
 * @author Lex
 * @ClassName: RegexFormatValidateExecutor
 * @Description: 正则表达式验证
 * @date 2014/4/10 15:59
 */
public class Bjkl8FormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {

    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        System.out.println(validatedBean.getBetDetail());
        String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),
                validatedBean.getGameOrder().getFileMode().getValue(), "\\|");
        String str = "上、中、下、奇、和、偶、大单、大双、小单小双";
        ValidateUtils.checkRepeat(bets);
        for(String bet : bets) {
            if (str.indexOf(bet)<=-1) {
                throw new GameBetContentPatternErrorException();
            }
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
