package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.Map;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatRXDSValidateExecutor 
* @Description:  n115任选几中几单式投注 格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class N115FormatRXDSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
      //n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	  private Map<Integer, Integer> numMap;

	  public void setNumMap(Map<Integer, Integer> numMap) {
	        this.numMap = numMap;
	    }
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//n115 单式投注 每注之间用逗号隔开  ，每注的投注号之间用空格分开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ",");
		for(String bet:bets){
			String  temp[]=ValidateUtils.convertBet2String(bet, fileMode.nuFile._value, " ");
			Integer length = numMap.get(validatedBean.getGameBetType().getGameGroupCode());
			//验证通过分割符分隔后的位数
			ValidateUtils.valideateBetformatAtleast(temp.length, length);
			//验证每项都是数字并且验证重复数据 
			ValidateUtils.checkIsNumber(temp);
			ValidateUtils.checkRepeat(temp);
		}
		
		((BetValidateContext) context).setBets(bets);
	}
}
