package com.winterframework.firefrog.game.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.business.MethodCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/**
 * 
* @ClassName: BetNumberYMBDWValidateExecutor 
* @Description: 一码不定位注数验证
* @author Richard
* @date 2013-8-6 上午10:16:57 
*
 */
public class BetNumberBDWAndQWValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	
	/** 
	* @Fields methodCodeSingleBetNumberMap : 获取配置参数 
	*/
	private MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap;

	public void setMethodCodeSingleBetNumberMap(MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap) {
		this.methodCodeSingleBetNumberMap = methodCodeSingleBetNumberMap;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();
		
		//获取不同不定位所需要的最小参数配置
		String length = methodCodeSingleBetNumberMap.getMethodCodeSingleBetNumberMap().get(
				validatedBean.getGameBetType().getBetMethodCode().toString());

		ValidateUtils.valideateBetformatAtleast(bets.length, Integer.parseInt(length));

		//验证各位各不相同
		ValidateUtils.checkRepeat(bets);

		//验证投注数
		int totbets = CombinUtil.combin(bets.length,Integer.parseInt(length));
		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}

	public MethodCodeSingleBetNumberMapConfig getMethodCodeSingleBetNumberMap() {
		return methodCodeSingleBetNumberMap;
	}
	
	
}
