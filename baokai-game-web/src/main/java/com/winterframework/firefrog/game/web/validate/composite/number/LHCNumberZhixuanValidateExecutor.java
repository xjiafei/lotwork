package com.winterframework.firefrog.game.web.validate.composite.number;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/** 
* @ClassName: LHCNumberZhixuanValidateExecutor 
* @Description:  六合彩直選投注煮熟驗證
* @author  
* @date 2014-4-10 下午3:54:35 
*  
*/
public class LHCNumberZhixuanValidateExecutor extends CompositeValidateExecutor<GameSlip> {
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
        if (bets.length <= 49 && num <= 1) {
            totalBets = CombinUtil.combin(bets.length, num);
        }
    	ValidateUtils.validateBetsCount(totalBets, validatedBean.getTotalBet());
        ((BetValidateContext) context).setTotalBets(totalBets);

    }
}
