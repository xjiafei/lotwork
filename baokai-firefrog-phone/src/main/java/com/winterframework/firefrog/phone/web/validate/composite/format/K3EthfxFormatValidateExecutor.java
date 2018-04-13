package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

public class K3EthfxFormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(K3STHFormatValidateExecutor.class);

	private static List<String> fotmatList = new ArrayList<String>();
	static {
		fotmatList.add("11*");
		fotmatList.add("22*");
		fotmatList.add("33*");
		fotmatList.add("44*");
		fotmatList.add("55*");
		fotmatList.add("66*");
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), 0, ",");
		for(String bet:bets){
			if(!fotmatList.contains(bet)){
				log.error("投注内容格式有误");
				throw new GameBetContentPatternErrorException();
			}
		}
		ValidateUtils.checkRepeat(bets);
		if (validatedBean.getTotalBet() != bets.length) {
			log.error("投注注数有误");
			throw new GameTotbetsErrorException();
		}
		((BetValidateContext) context).setBets(bets);
	}
}
