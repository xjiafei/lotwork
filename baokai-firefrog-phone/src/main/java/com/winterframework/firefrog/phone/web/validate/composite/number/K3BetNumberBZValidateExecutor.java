package com.winterframework.firefrog.phone.web.validate.composite.number;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.MethodCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.phone.web.validate.utils.CombinUtil;

public class K3BetNumberBZValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	
	/** 
	* @Fields methodCodeSingleBetNumberMap : 获取配置参数 
	*/
	private MethodCodeSingleBetNumberMapConfig k3BzMapConfig;

	public MethodCodeSingleBetNumberMapConfig getK3BzMapConfig() {
		return k3BzMapConfig;
	}

	public void setK3BzMapConfig(MethodCodeSingleBetNumberMapConfig k3BzMapConfig) {
		this.k3BzMapConfig = k3BzMapConfig;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) {

		String[] bets = ((BetValidateContext) context).getBets();
		
		//获取不同不定位所需要的最小参数配置
		String length = k3BzMapConfig.getMethodCodeSingleBetNumberMap().get(
				validatedBean.getGameBetType().getGameGroupCode().toString()
				+ validatedBean.getGameBetType().getGameSetCode().toString()
				+ validatedBean.getGameBetType().getBetMethodCode().toString());

		ValidateUtils.valideateBetformatAtleast(bets.length, Integer.parseInt(length));

		//验证各位各不相同
		ValidateUtils.checkRepeat(bets);

		//验证投注数
		int totbets = CombinUtil.combin(bets.length,Integer.parseInt(length));
		ValidateUtils.validateBetsCount(totbets, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totbets);
	}
}
