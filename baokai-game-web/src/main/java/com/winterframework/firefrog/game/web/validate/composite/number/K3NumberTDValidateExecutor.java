package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

import java.util.Map;

/** 
* @ClassName: N115NumberRXFSValidateExecutor 
* @Description:  n115任选复式投注 注数验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class K3NumberTDValidateExecutor extends CompositeValidateExecutor<GameSlip> {
      //n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	  private Map<Integer, Integer> numMap;

	  public void setNumMap(Map<Integer, Integer> numMap) {
	        this.numMap = numMap;
	    }
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
	    String[] danMa = ((BetValidateContext) context).getBets();
	    String[] tuoMa=((BetValidateContext) context).getAssitBets();
	    //选x中x;胆码数=m;拖码数=n combin(n,x-m)
		int totalBet=CombinUtil.combin(tuoMa.length, numMap.get(validatedBean.getGameBetType().getGameGroupCode())-danMa.length);

		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
