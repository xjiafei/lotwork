package com.winterframework.firefrog.phone.web.validate.composite.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSQFormatDTValidateExecutor 
* @Description:  双色球胆拖
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class SSQFormatDTValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		//拖胆投注正则表达式 胆码数和拖码数均为多个
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "\\+");
		if(bets.length!=2){
			throw new GameBetContentPatternErrorException();
		}
		//验证篮球
		String blueBalls[]=ValidateUtils.convertBet2String(bets[1], fileMode.nuFile._value, ",");
		ValidateUtils.checkIsNumber(blueBalls);
		ValidateUtils.checkRepeat(blueBalls);
		for(String blueBall:blueBalls){
			if(Integer.valueOf(blueBall)<1||Integer.valueOf(blueBall)>16||blueBall.length()!=2){//检测篮球是否在1-16之间的数字
				throw new GameBetContentPatternErrorException();
			}
		}
		
		if(blueBalls.length>8||blueBalls.length<1){//篮球号最少一个，最多8个
			throw new GameBetContentPatternErrorException();
		}
		
		//获取胆码和拖码
		String findTuoMa = "(?<=\\_T:).*(?=\\+)";//获取托码的正则表达式
		String findDanMa = "(?<=D:).*(?=\\_T)";//获取胆码的正则表达式
		String danMa = "";
		String tuoMa = "";
		Pattern pt = Pattern.compile(findTuoMa);
		Pattern pd = Pattern.compile(findDanMa);
		Matcher mt = pt.matcher(validatedBean.getBetDetail());
		Matcher md = pd.matcher(validatedBean.getBetDetail());
		while (mt.find()) {
			tuoMa = mt.group().trim();
		}
		while (md.find()) {
			danMa = md.group().trim();
		}
		
		if(tuoMa.isEmpty()||danMa.isEmpty()){
			throw new GameBetContentPatternErrorException();
		}
		
		//验证拖码胆码
		String[] dms=ValidateUtils.convertBet2String(danMa, fileMode.nuFile._value, ",");
		String[] tms=ValidateUtils.convertBet2String(tuoMa, fileMode.nuFile._value, ",");
		if(dms.length>5||dms.length<1){
			throw new GameBetContentPatternErrorException();
		}
		if(dms.length+tms.length>12||dms.length+tms.length<6){
			throw new GameBetContentPatternErrorException();
		}
		
		ValidateUtils.checkIsNumber(dms);
		ValidateUtils.checkRepeat(dms);
		ValidateUtils.checkIsNumber(tms);
		ValidateUtils.checkRepeat(tms);
		for(String strDm:dms){
			if(strDm.length()!=2){
				throw new GameBetContentPatternErrorException();
			}
		}
		for(String strTm:tms){
			if(strTm.length()!=2){
				throw new GameBetContentPatternErrorException();
			}
		}
		
		for(String strDm:dms){
			for(String strTm:tms)
			if(strTm.equals(strDm)){
				throw new GameBetContentPatternErrorException();
			}
		}
		
		((BetValidateContext) context).setBets(bets);
	}
}
