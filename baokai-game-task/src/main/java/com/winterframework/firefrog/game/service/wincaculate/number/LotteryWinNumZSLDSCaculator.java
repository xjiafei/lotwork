package com.winterframework.firefrog.game.service.wincaculate.number;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理如下彩种 
 * 前三组选组三单式 0, 3，true，2
 * 前三组选组六单式 0, 3，true，3
 * 前三组选混合组选0, 3，false
 * 后三组选组三单式2,5，true，2
 * 后三组选组六单式2,5，true，3
 * 前三组选混合组选2,5，false
*/
public class LotteryWinNumZSLDSCaculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 是否检查重复号码 
	*/
	private boolean checkCountNum;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		int totalBet = 0;//中奖注数
		resultCode = CaculateUtil.dealResultCode(resultCode, context.getResultCodeBeginIndex(), context.getResultCodeEndIndex());
		//		if (checkCountNum) {
		//			if (CaculateUtil.count(resultCode) != countNum) {
		//				return 0;
		//			}
		//		}
		//List<String> z3ds = CaculateUtil.pailie(resultCode);//获取开奖号码所有的排列
		//for (String s : z3ds) {
			
			String[] rNumbers = CaculateUtil.getResultNumbers(resultCode);
			String[] bNumbers = CaculateUtil.getResultNumbers(betDetail);
			Arrays.sort(rNumbers);
			Arrays.sort(bNumbers);
			Integer win =Arrays.equals(rNumbers, bNumbers)== true ? 1 : 0;
			totalBet = totalBet + win;
			
//			if (betDetail.contains(s)) {
//				totalBet++;
//			}
		//}
		//如果是false则为混合组选
		if (!checkCountNum) {
			String prefix = "";
			//组三
			if (CaculateUtil.count(resultCode) == 2) {
				prefix = "62";
			} else {
				//组六
				prefix = "63";
			}
			Map<String, Integer> resultMap = new HashMap<String, Integer>();
			resultMap.put(prefix, totalBet);
			return generateWinResultMultBean(resultMap, context);
		}

		return generateWinResultSingleBean(totalBet);
	}

	public void setCheckCountNum(boolean checkCountNum) {
		this.checkCountNum = checkCountNum;
	}
}
