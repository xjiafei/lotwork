package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.HZandKDRangeMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: BetFormatHZValidateExecutor 
* @Description: 和值投注
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatHZandKDValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private HZandKDRangeMapConfig HZandKDRangeMapConfig;

	public void setHZandKDRangeMapConfig(HZandKDRangeMapConfig hZandKDRangeMapConfig) {
		HZandKDRangeMapConfig = hZandKDRangeMapConfig;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), ",");
		String range = HZandKDRangeMapConfig.getHZandKDRangeMap().get(
				validatedBean.getGameBetType().getGameGroupCode().toString() + validatedBean.getGameBetType().getGameSetCode().toString()
						+ validatedBean.getGameBetType().getBetMethodCode().toString());
		//注数大于1即可
		ValidateUtils.valideateBetformatAtleast(bets.length, 1);
		
		ValidateUtils.checkIsNumber(bets);

		ValidateUtils.checkRepeat(bets);

		String[] rangeString = range.split(",");
		int rangeMax = Integer.parseInt(rangeString[1]);
		int rangeMin = Integer.parseInt(rangeString[0]);

		int betMax = ValidateUtils.maxValue(bets);

		int betMin = ValidateUtils.minValue(bets);

		ValidateUtils.valideateBetNumberRange(rangeMax, rangeMin, betMax, betMin);

		((BetValidateContext) context).setBets(bets);
	}

	public HZandKDRangeMapConfig getHZandKDRangeMapConfig() {
		return HZandKDRangeMapConfig;
	}
	
}
