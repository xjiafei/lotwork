package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatRXFSValidateExecutor 
* @Description:  n115任选几中几复式投注 格式验证,同样适用于任选不定位复式,任选二组选复式，任选三组选复式
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatRXFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
      //n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	  private Map<Integer, Integer> numMap;

	  public void setNumMap(Map<Integer, Integer> numMap) {
	        this.numMap = numMap;
	    }
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//复式投注以逗号隔开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ",");
		Integer length = numMap.get(validatedBean.getGameBetType().getGameGroupCode());
		//验证通过分割符分隔后的位数
		ValidateUtils.valideateBetformatAtleast(bets.length, length);
		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);
		((BetValidateContext) context).setBets(bets);
	}
}
