package com.winterframework.firefrog.phone.web.validate.composite.format;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSQFormatDSValidateExecutor 
* @Description:  ssq 单式格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class SSQFormatDSValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//双色球单式格式  ，每注的投注号之间用空格分开
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, ";");
		for(String bet:bets){
			String  temp[]=ValidateUtils.convertBet2String(bet, fileMode.nuFile._value, "\\+");
			if(temp.length!=2){
				throw new GameBetContentPatternErrorException();
			}
			ValidateUtils.checkIsNumber(temp[1]);//检测篮球
			if(Integer.valueOf(temp[1])<1||Integer.valueOf(temp[1])>16){//检测篮球是否在1-16之间的数字
				throw new GameBetContentPatternErrorException();
			}
			String redBalls[]=temp[0].split(",");
			if(redBalls.length!=6){//红球检测
				throw new GameBetContentPatternErrorException();
			}
			ValidateUtils.checkIsNumber(redBalls);
			ValidateUtils.checkRepeat(redBalls);
			for(String redBall:redBalls){
				if(Integer.valueOf(redBall)<1||Integer.valueOf(redBall)>33){//检测红球是否在1-33之间的数字
					throw new GameBetContentPatternErrorException();
				}
			}
		}
		
		((BetValidateContext) context).setBets(bets);
	}
}
