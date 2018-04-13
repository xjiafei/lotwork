package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatDWDFSValidateExecutor 
* @Description:  n115任选一中一定位胆复式投注 格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatDWDFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//复式投注以逗号隔开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ",");
		//验证通过分割符分隔后的位数,任选一中一是五位
		ValidateUtils.valideateBetformatAtleast(bets.length, 5);
        //每一位的号球由空格隔开
		for (String bet : bets) {
			if (!bet.contains("-")) {
				String temp[] = ValidateUtils.convertBet2String(bet, fileMode.nuFile._value, " ");
				//验证每项都是数字并且验证重复数据 
				ValidateUtils.checkIsNumber(temp);
				ValidateUtils.checkRepeat(temp);
			}

		}
		((BetValidateContext) context).setBets(bets);
	}
}
