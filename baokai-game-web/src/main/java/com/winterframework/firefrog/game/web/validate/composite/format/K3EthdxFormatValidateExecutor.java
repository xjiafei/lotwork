package com.winterframework.firefrog.game.web.validate.composite.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.validate.business.CompositeValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.business.GroupCodeBetContentRegexMapConfig;
import com.winterframework.firefrog.game.web.validate.business.GroupCodeSingleBetNumberMapConfig;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;

/** 
* @ClassName: SSLFormatFSValidateExecutor 
* @Description: 时时乐复式投注验证
* @author david 
* @date 2014-6-24 上午11:29:16 
*  
*/
public class K3EthdxFormatValidateExecutor extends CompositeValidateExecutor<GameSlip> {

	private static Logger log = LoggerFactory.getLogger(K3STHFormatValidateExecutor.class);
	private static List<String> fotmatList = new ArrayList<String>();
	static {
		fotmatList.add("11");
		fotmatList.add("22");
		fotmatList.add("33");
		fotmatList.add("44");
		fotmatList.add("55");
		fotmatList.add("66");
	}
	private static List<String> fotmatSecList = new ArrayList<String>();
	static {
		fotmatSecList.add("1");
		fotmatSecList.add("2");
		fotmatSecList.add("3");
		fotmatSecList.add("4");
		fotmatSecList.add("5");
		fotmatSecList.add("6");
	}
	@Override
	public void execute(GameSlip validatedBean, IValidateExecutorContext context) throws Exception {
		
		GameBetType gmaeBetType = validatedBean.getGameBetType();
		String betTypeCode = String.valueOf(gmaeBetType.getGameGroupCode()).concat("_")
							.concat(String.valueOf(gmaeBetType.getGameSetCode())).concat("_")
							.concat(String.valueOf(gmaeBetType.getBetMethodCode()));
							
		String[] betsEths = ValidateUtils.convertBet2String(validatedBean.getBetDetail(), fileMode.nuFile._value, "#");
		String[] betsEth = betsEths[0].split(" ");
		String[] betsY = betsEths[1].split(" ");

		ValidateUtils.checkIsNumber(betsEth);
		ValidateUtils.checkIsNumber(betsY);
		List<String> betsYList = Arrays.asList(betsY);
		for (int i = 0; i < betsEth.length; i++) {
			if (!fotmatList.contains(betsEth[i])) {
				log.error("投注内容格式有误");
				throw new GameBetContentPatternErrorException();
			}
			if (betsYList.contains(String.valueOf(Integer.valueOf(betsEth[i]) % 10))) {
				log.error("投注内容格式有误");
				throw new GameBetContentPatternErrorException();
			}
			if("40_34_77".equals(betTypeCode)){
				for(String betY : betsYList){
					if (!fotmatSecList.contains(betY)) {
						log.error("投注内容格式有误");
						throw new GameBetContentPatternErrorException();
					}
				}
			}
		}

		if (betsEth.length == 0 || betsEth.length > 5 || betsY.length == 0 || betsY.length > 5) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
		if (betsY.length + betsEth.length < 2 || betsY.length + betsEth.length > 6) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}

		if (validatedBean.getTotalBet() != betsY.length * betsEth.length) {
			log.error("投注注数有误");
			throw new GameTotbetsErrorException();
		}
		((BetValidateContext) context).setBets(betsEth);
		((BetValidateContext) context).setAssitBets(betsY);
	}
}
