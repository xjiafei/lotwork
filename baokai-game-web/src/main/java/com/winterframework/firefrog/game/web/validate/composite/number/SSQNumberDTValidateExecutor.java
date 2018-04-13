package com.winterframework.firefrog.game.web.validate.composite.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.firefrog.game.web.validate.utils.CombinUtil;

/** 
* @ClassName: SSQNumberDTValidateExecutor 
* @Description:  双色球胆拖格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class SSQNumberDTValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		String[] bets = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "\\+");
		String blueBalls[]=ValidateUtils.convertBet2String(bets[1], fileMode.nuFile._value, ",");
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
		String[] dms=ValidateUtils.convertBet2String(danMa, fileMode.nuFile._value, ",");
		String[] tms=ValidateUtils.convertBet2String(tuoMa, fileMode.nuFile._value, ",");
		int totalBet=CombinUtil.combin(tms.length, 6-dms.length)*blueBalls.length;

		ValidateUtils.validateBetsCount(totalBet, validatedBean.getTotalBet());

		((BetValidateContext) context).setTotalBets(totalBet);
	}
}
