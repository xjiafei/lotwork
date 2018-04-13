package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.MethodCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatBDWValidateExecutor 
* @Description: 不定位与趣味格式验证，需要配置分隔参数
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatBDWandQWValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	/** 
	* @Fields methodCodeSingleBetNumberMap : 获取配置参数 
	*/
	private MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap;

	public void setMethodCodeSingleBetNumberMap(MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap) {
		this.methodCodeSingleBetNumberMap = methodCodeSingleBetNumberMap;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), ",");

		//获取不同不定位所需要的最小参数配置
		String length = methodCodeSingleBetNumberMap.getMethodCodeSingleBetNumberMap().get(
				validatedBean.getGameBetType().getBetMethodCode().toString());
		//至少需要N个投注号
		ValidateUtils.valideateBetformatAtleast(bets.length, Integer.parseInt(length));
		//逗号分隔的每一位长度都是1
		for(String bet:bets){
			ValidateUtils.validateBetContentLength(bet, 1);
		}
		ValidateUtils.checkIsNumber(bets);

		//验证各位各不相同
		ValidateUtils.checkRepeat(bets);
		((BetValidateContext) context).setBets(bets);
	}

	public MethodCodeSingleBetNumberMapConfig getMethodCodeSingleBetNumberMap() {
		return methodCodeSingleBetNumberMap;
	}
}
