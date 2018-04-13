package com.winterframework.firefrog.game.web.validate.composite.format;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/**
 * @author Lex
 * @ClassName: Bjkl8FormatRenxuanValidateExecutor
 * @Description: 北京快乐8任选格式验证
 * @date 2014/4/3 14:22
 */
public class Bjkl8FormatRenxuanValidateExecutor extends CompositeValidateExecutor<GameSlip> {
    private static Logger log = LoggerFactory.getLogger(Bjkl8FormatRenxuanValidateExecutor.class);
    private Map<Integer, Integer> numMap;

    public void setNumMap(Map<Integer, Integer> numMap) {
        this.numMap = numMap;
    }

    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(),  validatedBean.getGameOrder().getFileMode().getValue(), ",");
        //如果投注数量小于任选数量抛出异常
        Integer num = numMap.get(validatedBean.getGameBetType().getBetMethodCode());
        if (bets.length < num) throw new GameBetContentPatternErrorException();
        ValidateUtils.checkRepeat(bets);
        for (String bet : bets) {
            try {
            	//若投注號碼不為2碼也是錯誤格式
				 if (Integer.parseInt(bet) > 80 || Integer.parseInt(bet) <= 0 || bet.length()!=2 || bet.matches("[0-9]+") == false){ 
                	throw new GameBetContentPatternErrorException();
                }
            } catch (NumberFormatException e) {
                log.error("投注内容格式有误,有重复");
                throw new GameBetContentPatternErrorException();
            }
        }
        ((BetValidateContext) context).setBets(bets);
    }
}
