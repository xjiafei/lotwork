package com.winterframework.firefrog.phone.web.validate.composite.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.HZandKDRangeMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

public class K3FormatBZValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	private static Logger log = LoggerFactory.getLogger(K3FormatBZValidateExecutor.class);
	//n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	private HZandKDRangeMapConfig rangeMapConfig;

	public HZandKDRangeMapConfig getRangeMapConfig() {
		return rangeMapConfig;
	}

	public void setRangeMapConfig(HZandKDRangeMapConfig rangeMapConfig) {
		this.rangeMapConfig = rangeMapConfig;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder()
				.getFileMode().getValue(), ",");
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);
		String range = rangeMapConfig.getHZandKDRangeMap().get(
				validatedBean.getGameBetType().getGameGroupCode().toString()
						+ validatedBean.getGameBetType().getGameSetCode().toString()
						+ validatedBean.getGameBetType().getBetMethodCode().toString());
		String[] rangeString = range.split(",");
		int rangeMax = Integer.parseInt(rangeString[1]);
		int rangeMin = Integer.parseInt(rangeString[0]);
		int betMax = ValidateUtils.maxValue(bets);
		int betMin = ValidateUtils.minValue(bets);
		if (bets.length < rangeMin || bets.length > rangeMax || betMax > rangeMax || betMin < 1) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
		((BetValidateContext) context).setBets(bets);
	}
}
