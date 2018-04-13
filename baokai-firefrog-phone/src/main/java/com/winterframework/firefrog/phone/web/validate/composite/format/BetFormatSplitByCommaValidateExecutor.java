package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.MethodCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatSplitByCommaValidateExecutor 
* @Description: 分割由逗号隔开的投注的内容，每个逗号隔开的都是一个数字，包含多个投注项，如五星组选120,4星组选24
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatSplitByCommaValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap;

	public void setMethodCodeSingleBetNumberMap(MethodCodeSingleBetNumberMapConfig methodCodeSingleBetNumberMap) {
		this.methodCodeSingleBetNumberMap = methodCodeSingleBetNumberMap;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), ",");

		String length = methodCodeSingleBetNumberMap.getMethodCodeSingleBetNumberMap().get(
				validatedBean.getGameBetType().getBetMethodCode().toString());

		//验证通过分割符分隔后的位数是两位
		ValidateUtils.valideateBetformatAtleast(bets.length, Integer.valueOf(length));
		//数组中每一项的长度是1
		for(String bet:bets){
			ValidateUtils.validateBetContentLength(bet, 1);
		}

		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);
		((BetValidateContext) context).setBets(bets);
	}

	public MethodCodeSingleBetNumberMapConfig getMethodCodeSingleBetNumberMap() {
		return methodCodeSingleBetNumberMap;
	}
}
