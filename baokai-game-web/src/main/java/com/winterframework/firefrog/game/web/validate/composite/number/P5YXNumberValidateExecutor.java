package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lex
 * @ClassName: Bjkl8NumberRenxuanValidateExecutor
 * @Description: 北京快乐8任选型投注数量验证
 * @date 2014/4/2
 */
public class P5YXNumberValidateExecutor extends CompositeValidateExecutor<GameSlip> {
    //任选数量，任选1，任选2...
    private Map<Integer, Integer> numMap;
    private static final Logger log = LoggerFactory
			.getLogger(P5YXNumberValidateExecutor.class);
    public void setNumMap(Map<Integer, Integer> numMap) {
        this.numMap = numMap;
    }

    @Override
    public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
        String[] bets = ((BetValidateContext) context).getBets();
        int totalBet = 0;
        for (String bet : bets) {
        	StringBuffer str = new StringBuffer();
        	log.info("------------------------");
        	log.info("bet = "+bet);
        	for(int i = 0;i<bet.length();i++){
        			str.append(bet.charAt(i)).append(" ");
        	}
        	log.info("str = "+str);
			String temp[] = ValidateUtils.convertBet2String(str.toString().trim(), fileMode.file._value, " ");
			if (temp[0].equals("") == false){
				totalBet += temp.length;
			}
		}
        ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());
        ((BetValidateContext) context).setTotalBets(totalBet);
	

    }
}
