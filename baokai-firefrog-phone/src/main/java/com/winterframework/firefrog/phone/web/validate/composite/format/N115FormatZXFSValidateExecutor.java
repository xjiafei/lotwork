package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatZXFSValidateExecutor 
* @Description:  n115选二选三直选复式格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatZXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	//选二选三直选格式正则
	private Map<Integer, String> regexMap;

	public void setRegexMap(Map<Integer, String> regexMap) {
		this.regexMap = regexMap;
	}
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String regex = regexMap.get(validatedBean.getGameBetType().getGameGroupCode());
		ValidateUtils.validateBetContentRegex(validatedBean.getBetDetail(), regex);
		//复式投注以逗号隔开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ",");
		//验证通过分割符分隔后的位数
		ValidateUtils.valideateBetformat(bets.length, 5);
		//验证每项都是数字并且验证重复数据 
		for (String bet : bets) {
			if (!bet.contains("-")) {
				ValidateUtils.checkIsNumber(bet.split(" "));
				ValidateUtils.checkRepeat(bet.split(" "));
			}
		}
		((BetValidateContext) context).setBets(bets);
	}
}
