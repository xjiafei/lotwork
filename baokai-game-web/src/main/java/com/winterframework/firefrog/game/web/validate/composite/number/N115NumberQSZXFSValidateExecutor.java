package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115NumberRXFSValidateExecutor 
* @Description:  n115选三前三直选复式投注 注数验证  算法 分第一位 号球数x  第二位号球数 y   第三位号球数z 一二重号位号球说xy 一三重号位号球数xz 二三重号位号球数yz  三重号号球数xyz 
* 算法 x*y*z -xy*z-xz*y-yz*x+2*xyz
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115NumberQSZXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
	    String[] bets = ((BetValidateContext) context).getBets();
	    //算法 x*y*z -xy*z-xz*y-yz*x+2xyz
	    int x=bets[0].split(" ").length;
	    int y=bets[1].split(" ").length;
	    int z=bets[2].split(" ").length;
	    int xy=0,xz=0,yz=0,xyz=0;
	    for (String bet:bets[0].split(" ")){
	    	if(bets[1].contains(bet)){
	    		xy++;
	    	}
	    	if(bets[2].contains(bet)){
	    		xz++;
	    	}
	    	if(bets[1].contains(bet)&&bets[2].contains(bet)){
	    		xyz++;
	    	}
	    }
	    for(String bet:bets[1].split(" ")){
	    	if(bets[2].contains(bet)){
	    		yz++;
	    	}
	    }
		int totalBet=x*y*z -xy*z-xz*y-yz*x+2*xyz;
		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
