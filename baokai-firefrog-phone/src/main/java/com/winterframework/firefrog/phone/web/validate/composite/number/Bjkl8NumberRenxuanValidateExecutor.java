package com.winterframework.firefrog.phone.web.validate.composite.number;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/**
 * @author Lex
 * @ClassName: Bjkl8NumberRenxuanValidateExecutor
 * @Description: 北京快乐8任选型投注数量验证
 * @date 2014/4/2
 */
public class Bjkl8NumberRenxuanValidateExecutor extends CompositeValidateExecutor<GameSlip> {
    //任选数量，任选1，任选2...
    private Map<Integer, Integer> numMap;

    public void setNumMap(Map<Integer, Integer> numMap) {
        this.numMap = numMap;
    }

    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        String[] bets = ((BetValidateContext) context).getBets();
        int totalBets = 0;
        Integer num = numMap.get(validatedBean.getGameBetType().getBetMethodCode());
        if (bets.length <= 80 && num <= 8) {
            totalBets = CombinUtil.combin(bets.length, num);
        }
        ((BetValidateContext) context).setTotalBets(totalBets);

    }
}
