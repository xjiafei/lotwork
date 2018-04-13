package com.winterframework.firefrog.game.web.validate.composite.format;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: N115FormatRXDSValidateExecutor 
* @Description:  n115任选几中几拖胆 格式验证
* @author david 
* @date 2014-4-10 下午3:54:35 
*  
*/
public class K3FormatTDValidateExecutor extends CompositeValidateExecutor<GameSlip> {
	//n115任选复式投注 每个需要验证的投注数量不一样    此处任选1 对应需要至少1位投注数任选二需要至少两位投注数  以此类推
	private Map<Integer, Integer> numMap;

	public void setNumMap(Map<Integer, Integer> numMap) {
		this.numMap = numMap;
	}

	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		Integer length = numMap.get(validatedBean.getGameBetType().getGameGroupCode());
		//拖胆投注正则表达式 胆码数和拖码数均为多个
		//String regex = "\\[胆(\\s*)(0[1-9]|10|11)(,(0[1-9]|10|11))*\\](\\s*)(0[1-9]|10|11)(,(0[1-9]|10|11))*";
		String regex = "D\\:(\\s*)([1-6])(,([1-6]))*\\_T\\:(\\s*)([1-6])(,([1-6]))*";
		ValidateUtils.validateBetContentRegex(validatedBean.getBetDetail(), regex);
		//胆码不能和拖码号有重复，用正则将胆码和拖码分别取出
		String find = "(?<=D\\:).*(?=\\_)";//获取胆码的正则表达式
		String danMa = "";
		String tuoMa = "";
		Pattern p = Pattern.compile(find);
		Matcher m = p.matcher(validatedBean.getBetDetail());
		while (m.find()) {
			danMa = m.group().trim();
		}
		//获取胆码数组
		String dMa[] = danMa.split(",");
		//验证胆码数量，因为胆码数不能超过玩法配置数-1
		ValidateUtils.valideateBetformatAtmost(dMa.length,length-1);
		//验证胆码每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(dMa);
		ValidateUtils.checkRepeat(dMa);
		//获取拖码数组
		String tMa[] = validatedBean.getBetDetail().split(":");
		tuoMa = tMa[2].trim();
		//n115 验证拖码数据
		String[] bets = ValidateUtils.convertBet2String(tuoMa, fileMode.nuFile._value, ",");
		//获取拖码需要的号球数
		//拖码需要的好球数验证 由于有胆码,至少需要的配置数为配置号球数-胆码数
		ValidateUtils.valideateBetformatAtleast(bets.length, length - dMa.length);
		//验证拖码每项都是数字并且验证重复数据 
		ValidateUtils.checkIsNumber(bets);
		ValidateUtils.checkRepeat(bets);
		//验证胆码的每一项在拖码中不能出现
		ValidateUtils.validateArrayItemNotInStr(dMa,tuoMa);
		//拖胆投注比较特殊，我们将胆码放置在投注字段中，拖码放置在辅助投注字段中
		((BetValidateContext) context).setBets(dMa);
		((BetValidateContext) context).setAssitBets(bets);
	}
}
