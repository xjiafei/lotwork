package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

import java.util.Map;

/**
 * @author Lex
 * @ClassName: RegexFormatValidateExecutor
 * @Description: 正则表达式验证
 * @date 2014/4/10 15:59
 */
public class RegexFormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {
    private Map<String, String> regex;

    public void setRegex(Map<String, String> regex) {
        this.regex = regex;
    }

    private String split;

    public void setSplit(String split) {
        this.split = split;
    }

    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        //正则表达式验证
        ValidateUtils.validateBetContentRegex(validatedBean.getBetDetail(),
                regex.get(validatedBean.getGameBetType().getGameGroupCode().toString() +
                        validatedBean.getGameBetType().getGameSetCode().toString() +
                        validatedBean.getGameBetType().getBetMethodCode()
                        ));
        String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),
                validatedBean.getGameOrder().getFileMode().getValue(),
                split == null ? "," : split);
        ((BetValidateContext) context).setBets(bets);
    }
}
