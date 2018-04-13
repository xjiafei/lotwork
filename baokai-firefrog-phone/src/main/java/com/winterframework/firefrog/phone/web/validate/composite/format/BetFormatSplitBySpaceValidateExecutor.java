package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.GroupCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SplitBySpaceValidateExecutor 
* @Description: 分割由空格隔开 适合单式投注
* @author david 
* @date 2013-8-5 下午6:01:22 
*  
*/
public class BetFormatSplitBySpaceValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private GroupCodeSingleBetNumberMapConfig groupCodeSingleBetNumberMapConfig;

	public void setGroupCodeSingleBetNumberMapConfig(GroupCodeSingleBetNumberMapConfig groupCodeSingleBetNumberMapConfig) {
		this.groupCodeSingleBetNumberMapConfig = groupCodeSingleBetNumberMapConfig;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), validatedBean.getGameOrder().getFileMode().getValue(), " ");
		//验证每项都是数字并且验证重复数据并且验证位数
		String length = groupCodeSingleBetNumberMapConfig.getGroupCodeSingleBetNumberMap().get(
				(validatedBean.getGameBetType().getGameGroupCode().toString()));
		ValidateUtils.checkIsNumber(bets);
		for (String bet : bets) {
			ValidateUtils.validateBetContentLength(bet, Integer.parseInt(length));
		}
		ValidateUtils.checkRepeat(bets);
		((BetValidateContext) context).setBets(bets);
	}

	public GroupCodeSingleBetNumberMapConfig getGroupCodeSingleBetNumberMapConfig() {
		return groupCodeSingleBetNumberMapConfig;
	}
	
	
}
