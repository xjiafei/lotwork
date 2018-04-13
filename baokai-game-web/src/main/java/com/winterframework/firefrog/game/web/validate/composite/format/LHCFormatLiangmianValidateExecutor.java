package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * 六合彩 兩面格式檢核
 * @author user
 *
 */
public class LHCFormatLiangmianValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),
                validatedBean.getGameOrder().getFileMode().getValue(), "\\|");
        String str = "大、小、和大、和小、单、双、和单、和双、大肖、小肖、尾大、尾小";
        ValidateUtils.checkRepeat(bets);
        for(String bet : bets) {
            if (str.indexOf(bet)<=-1) {
                throw new GameBetContentPatternErrorException();
            }
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
