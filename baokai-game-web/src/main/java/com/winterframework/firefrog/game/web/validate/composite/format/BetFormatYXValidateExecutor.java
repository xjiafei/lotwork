package com.winterframework.firefrog.game.web.validate.composite.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatYXValidateExecutor 
* @Description: 一星投注格式验证
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatYXValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	private static Logger log = LoggerFactory.getLogger(BetFormatYXValidateExecutor.class);
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
//		String regex="[-|[0-9]{1,}],[-|[0-9]{1,}],[-|[0-9]{1,}],[-|[0-9]{1,}],[-|[0-9]{1,}]";
		String regex="[0-9|-]{1,},[0-9|-]{1,},[0-9|-]{1,},[0-9|-]{1,},[0-9|-]{1,}";
		String regex2="-,-,-,-,-";
		if(validatedBean.getBetDetail().matches(regex2)||!validatedBean.getBetDetail().matches(regex)){
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.file._value, ",");
		ValidateUtils.checkIsNumber(bets);
		for (String bet : bets) {
			ValidateUtils.checkRepeat(bet);
		}
		((BetValidateContext) context).setBets(bets);
	}
}
