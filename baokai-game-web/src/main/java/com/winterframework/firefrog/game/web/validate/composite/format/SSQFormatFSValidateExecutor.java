package com.winterframework.firefrog.game.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSQFormatFSValidateExecutor 
* @Description:  双色球复式投注验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class SSQFormatFSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//复式投注以逗号隔开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "\\+");
		if(bets.length!=2){
			throw new GameBetContentPatternErrorException();
		}
		
		String redBalls[]=ValidateUtils.convertBet2String(bets[0], fileMode.nuFile._value, ",");
		String blueBalls[]=ValidateUtils.convertBet2String(bets[1], fileMode.nuFile._value, ",");
		
		if(redBalls.length<6||blueBalls.length<1){
			throw new GameBetContentPatternErrorException();
		}
		//验证通过分割符分隔后的位数
		//验证每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(redBalls);
		ValidateUtils.checkRepeat(redBalls);
		ValidateUtils.checkIsNumber(blueBalls);
		ValidateUtils.checkRepeat(blueBalls);
		for(String redBall:redBalls){
			if(Integer.valueOf(redBall)<1||Integer.valueOf(redBall)>33){//检测篮球是否在1-16之间的数字
				throw new GameBetContentPatternErrorException();
			}
		}
		for(String blueBall:blueBalls){
			if(Integer.valueOf(blueBall)<1||Integer.valueOf(blueBall)>16){//检测篮球是否在1-16之间的数字
				throw new GameBetContentPatternErrorException();
			}
		}
		((BetValidateContext) context).setBets(bets);
	}
}
