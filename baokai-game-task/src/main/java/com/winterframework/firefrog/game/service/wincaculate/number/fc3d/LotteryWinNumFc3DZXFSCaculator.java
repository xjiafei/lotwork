package com.winterframework.firefrog.game.service.wincaculate.number.fc3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/** 
 * 处理如下彩种
 * 五星直选复式  1
 * 四星直选复式 2
 * 前三直选复式 3
 * 后三直选复式 4
 * 后二直选复式 5
 * 前二直选复式 6
*/
public class LotteryWinNumFc3DZXFSCaculator extends AbstractLotteryWinSlipNumCaculator {

	private int lotteryType;
	
	private static final Logger log = LoggerFactory.getLogger(LotteryWinNumFc3DZXFSCaculator.class);

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		
		log.info("LotteryWinNumFc3DZXFSCaculator, 投注内容：BetDetail="+ betDetail + ", 开奖号码 resultCode="+ resultCode);
		String[] numbers = CaculateUtil.getResultNumbers(resultCode);//获取开奖号码分割后的字符串数组
		String regex = "";//正则表达式
		switch (lotteryType) {
		case 1://五星直选复式
			regex = "\\d*" + numbers[0] + "\\d*,\\d*" + numbers[1] + "\\d*,\\d*" + numbers[2] + "\\d*,\\d*"
					+ numbers[3] + "\\d*,\\d*" + numbers[4] + "\\d*";
			break;
		case 2://四星直选复式
			regex = "-,\\d*" + numbers[1] + "\\d*,\\d*" + numbers[2] + "\\d*,\\d*" + numbers[3] + "\\d*,\\d*"
					+ numbers[4] + "\\d*";
			break;
		case 3://前三直选复式
			regex = "\\d*" + numbers[0] + "\\d*,\\d*" + numbers[1] + "\\d*,\\d*" + numbers[2] + "\\d*";
			break;
		case 4://后三直选复式
			regex = "\\d*" + numbers[0] + "\\d*,\\d*" + numbers[1] + "\\d*,\\d*" + numbers[2] + "\\d*";
			break;
		case 5://后二直选复式
			regex = "-,\\d*" + numbers[1] + "\\d*,\\d*" + numbers[2] + "\\d*";
			break;
		case 6://前二直选复式
			regex = "\\d*" + numbers[0] + "\\d*,\\d*" + numbers[1] + "\\d*,-";
			break;
		}
		log.info("LotteryWinNumFc3DZXFSCaculator, 投注内容：BetDetail="+ betDetail + ", 开奖号码 resultCode="+ resultCode + ",regex =" + regex);
		return generateWinResultSingleBean(betDetail.trim().matches(regex) == true ? 1 : 0);
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}

}