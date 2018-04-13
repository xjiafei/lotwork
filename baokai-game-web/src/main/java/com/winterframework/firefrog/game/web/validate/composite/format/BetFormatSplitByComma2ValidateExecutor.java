package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SplitByComma2ValidateExecutor 
* @Description: 分割由逗号隔开的投注内容，适合单式投注
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatSplitByComma2ValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), ",");

		int length = bets.length;

		//验证通过分割符分隔后的位数是两位
		ValidateUtils.valideateBetformat(length, 2);

		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		for(String bet:bets){
			ValidateUtils.checkRepeat(bet);
		}
		((BetValidateContext) context).setBets(bets);
	}
}
