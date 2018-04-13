package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115NumberQEZXFSValidateExecutor 
* @Description:  n115选二前二复式投注 注数验证  算法 分第一位 号球数m  第二位好球数 n  重号数 p  (m-p)*n+C(p,2)+(n-p)*p=mn-p
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115NumberQEZXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
	    String[] bets = ((BetValidateContext) context).getBets();
	    //n115选二前二复式投注 注数验证  算法 分第一位 号球数m  第二位好球数 n  重号数 p  (m-p)*n+C(p,2)+(n-p)*p=mn-p
	    int m=bets[0].split(" ").length;
	    int n=bets[1].split(" ").length;
	    int p=0;
	    for (String bet:bets[0].split(" ")){
	    	if(bets[1].contains(bet)){
	    		p++;
	    	}
	    }
		int totalBet=m*n-p;
		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
