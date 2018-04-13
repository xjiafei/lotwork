package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatDWDFSValidateExecutor 
* @Description:  n115趣味猜中位投注 格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatCZWValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String regex = "(0[3-9])(,(0[3-9]))*";
		ValidateUtils.validateBetContentRegex(validatedBean.getBetDetail(), regex);
		//复式投注以逗号隔开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ",");
		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);

		((BetValidateContext) context).setBets(bets);
	}
}
