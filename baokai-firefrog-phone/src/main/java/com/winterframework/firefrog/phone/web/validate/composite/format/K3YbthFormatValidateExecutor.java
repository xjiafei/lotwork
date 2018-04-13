package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

public class K3YbthFormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(K3STHFormatValidateExecutor.class);
	private static List<String> fotmatList = new ArrayList<String>();
	static {
		fotmatList.add("1");
		fotmatList.add("2");
		fotmatList.add("3");
		fotmatList.add("4");
		fotmatList.add("5");
		fotmatList.add("6");
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] betsEths = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), 0, "#");
		String[] numbers = betsEths[0].split(",");
		ValidateUtils.checkIsNumber(numbers);
		for (int i = 0; i < numbers.length; i++) {
			if (!fotmatList.contains(numbers[i])) {
				log.error("投注内容格式有误");
				throw new GameBetContentPatternErrorException();
			}
		}
		if (betsEths.length == 0 || betsEths.length > 1) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
		/*if(validatedBean.getTotalBet()!=1){
			log.error("投注注数有误");
			throw new GameTotbetsErrorException();
		}*/
		((BetValidateContext) context).setBets(betsEths);
	}
}
