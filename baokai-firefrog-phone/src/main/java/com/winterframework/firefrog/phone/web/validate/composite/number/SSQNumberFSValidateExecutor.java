package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

/** 
* @ClassName: SSQNumberFSValidateExecutor 
* @Description:  双色球复式格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class SSQNumberFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "\\+");
		String redBalls[]=ValidateUtils.convertBet2String(bets[0], fileMode.nuFile._value, ",");
		String blueBalls[]=ValidateUtils.convertBet2String(bets[1], fileMode.nuFile._value, ",");
		
		int totalBet=CombinUtil.combin(redBalls.length,6)*blueBalls.length;
		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());
		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
