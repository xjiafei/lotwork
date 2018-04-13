package com.winterframework.firefrog.phone.web.validate.composite.number;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/** 
* @ClassName: N115NumberRXFSValidateExecutor 
* @Description:  n115任选复式投注 注数验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115NumberRXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
      //n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	  private Map<Integer, Integer> numMap;

	  public void setNumMap(Map<Integer, Integer> numMap) {
	        this.numMap = numMap;
	    }
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
	    String[] bets = ((BetValidateContext) context).getBets();
		
		int totalBet=CombinUtil.combin(bets.length, numMap.get(validatedBean.getGameBetType().getGameGroupCode()));

		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
