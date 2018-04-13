package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.GroupCodeBetContentRegexMapConfig;
import com.winterframework.firefrog.phone.web.validate.business.GroupCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSLFormatFSValidateExecutor 
* @Description: 时时乐复式投注验证
* @author david 
* @date 2014-6-24 上午11:29:16 
*  
*/
public class SSLFormatFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private GroupCodeBetContentRegexMapConfig groupCodeBetContentRegexMapConfig;
	private GroupCodeSingleBetNumberMapConfig groupCodeSingleBetNumberMapConfig;

	public void setGroupCodeSingleBetNumberMapConfig(GroupCodeSingleBetNumberMapConfig groupCodeSingleBetNumberMapConfig) {
		this.groupCodeSingleBetNumberMapConfig = groupCodeSingleBetNumberMapConfig;
	}

	public void setGroupCodeBetContentRegexMapConfig(GroupCodeBetContentRegexMapConfig groupCodeBetContentRegexMapConfig) {
		this.groupCodeBetContentRegexMapConfig = groupCodeBetContentRegexMapConfig;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {

		//验证复式投注格式
		ValidateUtils.validateBetContentRegex(validatedBean.getBetDetail(), groupCodeBetContentRegexMapConfig
				.getGroupCodeBetContentRegexMap().get(validatedBean.getGameBetType().getGameGroupCode().toString()));
		//复式投注含有- 需要以文件模式分割
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.file._value, ",");
		String length = groupCodeSingleBetNumberMapConfig.getGroupCodeSingleBetNumberMap().get(
				validatedBean.getGameBetType().getGameGroupCode().toString());
		//验证通过分割符分隔后的位数
		ValidateUtils.valideateBetformat(bets.length, Integer.parseInt(length));

		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		((BetValidateContext) context).setBets(bets);
	}
}
