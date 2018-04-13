package com.winterframework.firefrog.game.web.validate.composite.format;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.business.GroupCodeBetContentRegexMapConfig;
import com.winterframework.firefrog.game.web.validate.business.GroupCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSLFormatFSValidateExecutor 
* @Description: 时时乐复式投注验证
* @author david 
* @date 2014-6-24 上午11:29:16 
*  
*/
public class K3SLHFormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(K3STHFormatValidateExecutor.class);

	private static String SLH_TX_BETS = "123 234 345 456";

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, " ");
		if (!validatedBean.getBetDetail().equals(SLH_TX_BETS)) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}

		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);
		if (validatedBean.getTotalBet() != 1) {
			log.error("投注注数有误");
			throw new GameTotbetsErrorException();
		}
		((BetValidateContext) context).setBets(bets);
	}
}
